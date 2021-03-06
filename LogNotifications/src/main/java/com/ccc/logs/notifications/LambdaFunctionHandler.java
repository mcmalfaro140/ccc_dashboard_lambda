package com.ccc.logs.notifications;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * The starting class for the Lambda function. It is best to keep
 * this class stateless because it enables AWS Lambda to use the
 * same instance of this class whenever it needs to
 */
public class LambdaFunctionHandler implements RequestHandler<Map<String, Map<String, String>>, Integer> {
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
	 * @return 1 if an exception occurred, otherwise 0
	 */
    @Override
    public Integer handleRequest(Map<String, Map<String, String>> input, Context context) {
    	LogData logData = null;
    	LinkedList<LogAlarmData> logAlarmList = null;
    	StringBuilder cloudwatchLogData = new StringBuilder();
    	
    	try {
    		logData = LogParser.parse(input);
        	logAlarmList = this._getLogAlarms(logData);
        	this._handleLogEvents(logData, logAlarmList, cloudwatchLogData);
        	
        	return 0;
    	} catch (Throwable e) {
    		String stackTrace = this._getStackTraceAsString(e);
    		String exceptionMessage = String.format(
    			"An exception occurred. Some messages may not have been sent\n\n%s\n\nLog Data:\n%s\n\nLog Alarms:\n%s",
    			stackTrace,
    			Objects.toString(logData, input.get("awslogs").get("data")),
    			Objects.toString(logAlarmList, "Log Alarms Not Obtained")
    		);
    		
    		cloudwatchLogData.append(exceptionMessage);    		
    		AmazonSNSWrapper.publishToSNS(GlobalVariables.EXCEPTION_SNS_TOPIC_ARN, exceptionMessage);
    		
    		return 1;
    	} finally {
    		String invocationData = String.format(
        			"Max memory allocated in MB: %s\nTime remaining in milliseconds: %s",
        			context.getMemoryLimitInMB(),
        			context.getRemainingTimeInMillis()
        	);
    		
    		cloudwatchLogData.append(invocationData);
    		context.getLogger().log(cloudwatchLogData.toString());
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
    private LinkedList<LogAlarmData> _getLogAlarms(LogData logData) {
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
    private void _handleLogEvents(LogData logData, LinkedList<LogAlarmData> logAlarmList, StringBuilder cloudwatchLogData) {
    		StringBuilder publishResults = new StringBuilder();
    		
    		for (LogAlarmData logAlarm : logAlarmList) {			
    			for (LogEvent logEvent : logData.getLogEvents()) {
    				LogMessage logMessage = logEvent.getMessage();
    				
    				if (this._checkAlarm(logMessage, logAlarm)) {
    					for (String snsTopicArn : logAlarm.getSNSTopicArnList()) {
    						
    						PublishResult result = AmazonSNSWrapper.publishToSNS(
    								snsTopicArn,
    								logMessage.toString(),
    								logData.getLogGroup(),
    								logData.getLogStream()
    						);
    						String publishResultData = String.format(
    				    			"SNS Message Id: %s\nAWS Request Id for SNS message: %s\nHTTP Status Code: %d\nTopic ARN: %s\n",
    				    			result.getMessageId(),
    				    			result.getSdkResponseMetadata().getRequestId(),
    				    			result.getSdkHttpMetadata().getHttpStatusCode(),
    				    			snsTopicArn
    				    	);
    						
    						publishResults.append(publishResultData);
    					}
    				}
    			}
    		}
    		
    		cloudwatchLogData.append(publishResults);
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
    	return LevelComparer.compare(logMessage.getLevel(), logAlarm.getLogLevelData()) &&
    			KeywordSearcher.search(logMessage.getMessage(), logAlarm.getKeywordData());
    }
    
    /**
     * Converts the stack trace of a {@code Throwable} object
     * to a string. Called whenever any exception occurs
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
}
