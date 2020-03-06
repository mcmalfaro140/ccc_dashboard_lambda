package com.ccc.lambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
    	LogData logData = LogParser.parse(input);
    	this._sendMessages(logData);
    	
    	context.getLogger().log(logData.toString());
    	
    	return "";
    }
    
    private void _sendMessages(LogData logData) {
    	List<String> snsTopicList = this._getSNSTopics(logData);
    	
    	for (String snsTopic : snsTopicList) {
    		for (LogEvent logEvent : logData.getLogEvents()) {
    			AmazonSNSWrapper.publishToSNS(snsTopic, logEvent.getMessage().toString());
    		}
    	}
    }
    
    private List<String> _getSNSTopics(LogData logData) {
    	try(DatabaseConnector conn = new DatabaseConnector()) {
    		return conn.getSNSTopics(logData);
    	}
    }
}
