package com.ccc.lambda;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
    	LogData logData = LogParser.parse(input);
    	
    	try (DatabaseConnector conn = new DatabaseConnector()) {
    		this._sendMessages(logData, conn);
    	}
    	
    	return "";
    }
    
    private void _sendMessages(LogData logData, DatabaseConnector conn) {
    	String logGroup = logData.getLogGroup();
    	String logStream = logData.getLogStream();
    	ResultSet set = conn.getAlarmsForLogGroupsAndStreams(logGroup, logStream);
    	
    	try {
    		while (set.next()) {
    			String levelOfAlarm = set.getString("LogLevel");
    			String condition = set.getString("LogCondition");
    			String snsTopicArn = set.getString("SNSTopicArn");
    			
        		for (LogEvent event : logData.getLogEvents()) {
        			String levelOfEvent = event.getMessage().getLevel();
        			int comparison = LevelComparer.compare(levelOfEvent, levelOfAlarm);
        			
        			if ((0 == comparison && condition.equals("==")) || (comparison >= 0 && condition.equals(">="))) {
        				AmazonSNSWrapper.publishToSNS(snsTopicArn, event.getMessage().toString());
        			}
        		}
        	}
    	} catch (SQLException ex) {
    		throw new InternalError("Error while iterating through query results", ex);
    	}
    }
}
