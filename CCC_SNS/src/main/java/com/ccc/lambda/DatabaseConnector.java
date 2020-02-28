package com.ccc.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector implements AutoCloseable {
	private Connection conn;
	
	public DatabaseConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	this.conn = DriverManager.getConnection(AwsParams.DATABASE_URL, AwsParams.DATABASE_USERNAME, AwsParams.DATABASE_PASSWORD);
		} catch (SQLException|ClassNotFoundException ex) {
			throw new InternalError("Error while connecting to database", ex);
		}
	}
	
	public ResultSet getAlarmsForLogGroupsAndStreams(String logGroup, String logStream) {
		String sql = 
    			"SELECT DISTINCT LA.SNSTopicArn, LLC.LogLevel, LLC.LogCondition " + 
    			"FROM LogAlarms LA " + 
    			"INNER JOIN LogLevelCriteria LLC ON LA.LogLevelCriteriaId = LLC.LogLevelCriteriaId " +
    			"INNER JOIN XRefAlarmGroup XAG ON XAG.LogAlarmId = LA.LogAlarmId " +
    			"INNER JOIN LogGroups LG ON LG.LogGroupId = XAG.LogGroupId " +
    			"INNER JOIN XRefAlarmStream XAS ON XAS.LogAlarmId = LA.LogAlarmId " +
    			"INNER JOIN LogStreams LS ON LS.LogStreamId = XAS.LogStreamId " +
    			"WHERE LG.Name LIKE '%" + logGroup + "%' AND LS.Name LIKE '%" + logStream + "%';";
    			
    	try {
    		PreparedStatement stmt = this.conn.prepareStatement(sql);
    		ResultSet set = stmt.executeQuery();
    		
    		return set;
    	} catch (SQLException ex) {
    		String errorInfo = String.format(
    				"Error while retrieving alarms associated with the LogGroup={%s} and LogStream={%s}",
    				logGroup, logStream
    		);
    		
    		throw new InternalError(errorInfo, ex);
    	}
	}
	
	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException ex) {
			throw new InternalError("Error while closing connection to database");
		}
	}
}
