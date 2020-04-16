package com.ccc.logs.notifications;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper class for the object that holds the connection
 * to the database
 */
class DatabaseConnector implements Closeable, AutoCloseable {
	/**
	 * The object that holds the connection to the
	 * database
	 */
	private Connection conn;
	
	/**
	 * Initializes the connection to the database
	 */
	public DatabaseConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	this.conn = DriverManager.getConnection(GlobalVariables.DATABASE_URL, GlobalVariables.DATABASE_USERNAME, GlobalVariables.DATABASE_PASSWORD);
		} catch (SQLException|ClassNotFoundException ex) {
			throw new LogNotificationException("Error while connecting to database", ex);
		}
	}
	
	/**
	 * Retrieves the log alarms that are associated
	 * with the specified log group
	 * @param logGroup The log group corresponding
	 * to the log alarms to be obtained
	 * @return A list of log alarms and their trigger
	 * conditions
	 */
	public List<LogAlarmData> getLogAlarms(String logGroup) {
		String sql = 
				"SELECT LA.LogAlarmId, LA.LogLevel, LA.Comparison, LA.KeywordRelationship, " +
				"GROUP_CONCAT(DISTINCT ST.SNSTopicId) AS SNSTopicIds, " +
				"GROUP_CONCAT(DISTINCT ST.TopicArn) AS TopicArns, " +
				"GROUP_CONCAT(DISTINCT K.KeywordId) AS KeywordIds, " +
				"GROUP_CONCAT(DISTINCT K.Word) AS Keywords " +
				"FROM LogAlarms LA " +
				"NATURAL JOIN XRefLogAlarmLogGroup " +
				"NATURAL JOIN LogGroups LG " +
				"NATURAL JOIN XRefLogAlarmSNSTopic " +
				"NATURAL JOIN SNSTopics ST " +
				"LEFT JOIN XRefLogAlarmKeyword XRLAK ON LA.LogAlarmId = XRLAK.LogAlarmId " +
				"LEFT JOIN Keywords K ON XRLAK.KeywordId = K.KeywordId " +
				"WHERE LG.Name = ?" +
				"GROUP BY LA.LogAlarmId;";
		
		try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
			stmt.setString(1, logGroup);
			
			try (ResultSet set = stmt.executeQuery()) {
				return LogAlarmDataMapper.mapResultSet(set);
			}
		} catch (SQLException ex) {
			throw new LogNotificationException("Error while querying database", ex);
		}
	}
	
	/**
	 * Closes the connection to
	 * the database
	 */
	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException ex) {
			throw new LogNotificationException("Error while closing connection to database");
		}
	}
}
