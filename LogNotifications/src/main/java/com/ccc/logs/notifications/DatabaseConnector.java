package com.ccc.logs.notifications;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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
			throw new LogNotificationError("Error while connecting to database", ex);
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
	public LinkedList<LogAlarmData> getLogAlarms(String logGroup) {
		final String sql = 
				"SELECT LogLevel, Comparison, KeywordRelationship, " +
				"GROUP_CONCAT(DISTINCT TopicArn) AS TopicArns, " +
				"GROUP_CONCAT(DISTINCT Word) AS Keywords " +
				"FROM LogAlarms LA " +
				"NATURAL JOIN XRefLogAlarmLogGroup " +
				"NATURAL JOIN LogGroups " +
				"NATURAL JOIN XRefLogAlarmSNSTopic " +
				"NATURAL JOIN SNSTopics " +
				"LEFT JOIN XRefLogAlarmKeyword XRLAK ON LA.LogAlarmId = XRLAK.LogAlarmId " +
				"LEFT JOIN Keywords K ON XRLAK.KeywordId = K.KeywordId " +
				"WHERE Name = ? " +
				"GROUP BY LA.LogAlarmId;";
		
		try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
			stmt.setString(1, logGroup);
			
			try (ResultSet set = stmt.executeQuery()) {
				return LogAlarmDataMapper.mapResultSet(set);
			}
		} catch (SQLException ex) {
			throw new LogNotificationError("Error while querying database", ex);
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
			throw new LogNotificationError("Error while closing connection to database");
		}
	}
}
