package com.ccc.logs.notifications;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * The starting class for the Lambda function. It is best to keep
 * this class stateless because it enables AWS Lambda to use the
 * same instance of this class whenever it needs to
 */
public class LambdaFunctionHandler implements RequestHandler<Object, Integer> {
	/**
	 * Starting point for the Lambda function
	 * @param input The Base64 GZIP compressed
	 * data on the log data associated with this
	 * invocation of the Lambda function. If an
	 * exception occurs at any point while this
	 * Lambda function is running, this function
	 * will catch it and send some notifications
	 * indicating the exception happened
	 * @param context The Lambda execution environment context object
	 * @return Empty string
	 */
    @Override
    public Integer handleRequest(Object input, Context context) {
    	LambdaLogger logger = context.getLogger();
    	LogData logData = null;
    	List<LogAlarmData> logAlarmList = null;
    	
    	try {
    		logData = LogParser.parse(input);
        	logAlarmList = this._getLogAlarms(logData);
        	
        	if (logAlarmList.size() != 0) {
        		this._handleLogEvents(logData, logAlarmList, logger);
        	}
        	
        	return 0;
    	} catch (Throwable e) {
    		String stackTrace = this._getStackTraceAsString(e);
    		String exceptionMessage = "An exception occurred. Some messages may not have been sent\n" + stackTrace;
    		
    		logger.log(exceptionMessage);
    		AmazonSNSWrapper.publishToSNS(AWSParams.EXCEPTION_SNS_TOPIC_ARN, exceptionMessage);
    		
    		return 1;
    	} finally {
    		this._logInvocationData(logger, context, logData, logAlarmList);
    	}
    }
    
    /**
     * Returns the log alarms associated with the log
     * group specified in the log data for this
     * invocation of the Lambda function
     * @param logData The log data to be used to retrieve
     * the correct log alarms
     * @return The log alarms associated with the log group
     * specified in the log data for this invocation of the
     * Lambda function
     */
    private List<LogAlarmData> _getLogAlarms(LogData logData) {
    	try (DatabaseConnector conn = new DatabaseConnector()) {
    		return conn.getLogAlarms(logData.getLogGroup());
    	}
    }
    
    /**
     * Handles the processing of the log events associated
     * with this invocation of the Lambda function. Interfaces
     * with the AWS CloudWatch logger for this Lambda function
     * and the Amazon SNS wrapper
     * @param logData The log data to be processed
     * @param logAlarmList The list of log alarms to be checked
     * for whether or not any of them should be triggered
     * @param logger The AWS CloudWatch logger to use if any log
     * alarms are triggered
     */
    private void _handleLogEvents(LogData logData, List<LogAlarmData> logAlarmList, LambdaLogger logger) {  
    	for (LogEvent logEvent : logData.getLogEvents()) {
			LogMessage logMessage = logEvent.getMessage();
			
			for (LogAlarmData logAlarm : logAlarmList) {
				if (this._checkAlarm(logMessage, logAlarm)) {
					for (SNSTopicData snsTopicData : logAlarm.getSNSTopicDataList()) {
						PublishResult result = AmazonSNSWrapper.publishToSNS(snsTopicData.getTopicArn(), logMessage.toString());
						
						this._logPublishResult(logger, result);
					}
				}
			}
		}
    }
    
    /**
     * Checks if the given log should trigger the given log alarm
     * @param logMessage The log to be checked
     * @param logAlarm The log alarm criteria for the log to be
     * tested against
     * @return <tt>true</tt> if the given log should trigger the
     * given log alarm, <tt>false</tt> otherwise
     */
    private boolean _checkAlarm(LogMessage logMessage, LogAlarmData logAlarm) {
    	return LevelComparer.compare(logMessage.getLevel(), logAlarm.getLogLevelCriteriaData()) &&
    			KeywordSearcher.search(logMessage.getMessage(), logAlarm.getKeywordDataList());
    }
    
    /**
     * Logs data on the SNS publish to CloudWatch
     * @param logger The logger that logs to AWS CloudWatch
     * @param result The result of the SNS publish to log
     */
    private void _logPublishResult(LambdaLogger logger, PublishResult result) {
    	String publishResultData = String.format(
    			"SNS Message Id: %s\nAWS Request Id for SNS message: %s\nHTTP Status Code: %d",
    			result.getMessageId(),
    			result.getSdkResponseMetadata().getRequestId(),
    			result.getSdkHttpMetadata().getHttpStatusCode()
    	);
    	
    	logger.log(publishResultData);
    }
    
    /**
     * Converts the stack trace of a <tt>Throwable</tt> object
     * to a string. Called whenever any
     * @param e The exception that occurred
     * @return A string representation of the stack trace of
     * the exception that occurred
     */
    private String _getStackTraceAsString(Throwable e) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	e.printStackTrace(pw);
    	
    	return sw.toString();
    }
    
    /**
     * Logs data about this invocation of the Lambda function
     * to CloudWatch
     * @param logger The logger that logs to AWS CloudWatch
     * @param context The context object
     */
    private void _logInvocationData(LambdaLogger logger, Context context, LogData logData, List<LogAlarmData> logAlarmList) {
    	String invocationData = String.format(
    			"Max memory allocated in MB: %s\nTime remaining in milliseconds: %s\n%s\n%s",
    			context.getMemoryLimitInMB(),
    			context.getRemainingTimeInMillis(),
    			Objects.toString(logData),
    			Objects.toString(logAlarmList)
    	);
    	
    	logger.log(invocationData);
    }
}
