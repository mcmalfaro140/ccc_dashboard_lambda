package com.ccc.logs.notifications;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * The starting class for the Lambda function. It is best to keep
 * this class stateless because it enables AWS Lambda to use the
 * same instance of this class whenever it needs to
 */
public class LambdaFunctionHandler implements RequestHandler<Object, String> {
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
    public String handleRequest(Object input, Context context) {
    	LambdaLogger logger = context.getLogger();
    	
    	try {
    		LogData logData = LogParser.parse(input);
        	List<LogAlarmData> logAlarmList = this._getLogAlarms(logData);
        	this._handleLogEvents(logData, logAlarmList, logger);
    	} catch (Throwable e) {
    		String stackTrace = this._getStackTraceAsString(e);
    		
    		logger.log(stackTrace);
    		AmazonSNSWrapper.publishToSNS(AWSParams.EXCEPTION_SNS_TOPIC_ARN, stackTrace);
    		
    		throw e;
    	}
    	
    	this._logInvocationData(logger, context);
    	
    	return "";
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
    	DatabaseConnector conn = new DatabaseConnector();
    	List<LogAlarmData> logAlarmList = conn.getLogAlarms(logData.getLogGroup());
    	
    	conn.close();
    	
    	return logAlarmList;
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
						logger.log("Message Id: " + result.getMessageId());
					}
				}
			}
		}
    	
    	logger.log(logData.toString());
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
    			KeywordSearcher.search(logMessage.getMessage(), logAlarm.getKeywordData());
    }
    
    /**
     * Converts the stack trace of a <tt>Throwable</tt> object
     * to a string. Called whenever any
     * @param e The exception that occurred
     * @return A string representation of the stack trace of
     * the exception that occurred
     */
    private String _getStackTraceAsString(Throwable e) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (StackTraceElement elem : e.getStackTrace()) {
    		sb.append(elem);
    		sb.append('\n');
    	}
    	
    	return sb.toString();
    }
    
    private void _logInvocationData(LambdaLogger logger, Context context) {
    	logger.log("Function: " + context.getFunctionName());
    	logger.log("Max memory allocated in MB: " + context.getMemoryLimitInMB());
    	logger.log("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
    }
}
