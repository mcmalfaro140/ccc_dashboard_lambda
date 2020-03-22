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
        	this.conn = DriverManager.getConnection(AWSParams.DATABASE_URL, AWSParams.DATABASE_USERNAME, AWSParams.DATABASE_PASSWORD);
		} catch (SQLException|ClassNotFoundException ex) {
			throw new InternalError("Error while connecting to database", ex);
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
				"SELECT LA.LogAlarmId, LLC.LogLevelCriteriaId, LLC.LogLevel, LLC.Comparison, XRLAK.Relationship, " +
				"GROUP_CONCAT(DISTINCT ST.SNSTopicId) AS SNSTopicIds, " +
				"GROUP_CONCAT(DISTINCT ST.TopicArn) AS TopicArns, " +
				"GROUP_CONCAT(DISTINCT K.Word) AS Keywords " +
				"FROM LogAlarms LA " +
				"INNER JOIN XRefLogAlarmLogGroup XRLALG ON LA.LogAlarmId = XRLALG.LogAlarmId " +
				"INNER JOIN LogGroups LG ON XRLALG.LogGroupId = LG.LogGroupId " +
				"INNER JOIN LogLevelCriteria LLC ON LA.LogLevelCriteriaId = LLC.LogLevelCriteriaId " +
				"INNER JOIN XRefLogAlarmSNSTopic XRLAST ON LA.LogAlarmId = XRLAST.LogAlarmId " +
				"INNER JOIN SNSTopics ST ON XRLAST.SNSTopicId = ST.SNSTopicId " +
				"INNER JOIN XRefLogAlarmKeyword XRLAK ON LA.LogAlarmId = XRLAK.LogAlarmId " +
				"INNER JOIN Keywords K ON XRLAK.KeywordId = K.KeywordId " +
				"WHERE LG.Name = ? AND LA.LogAlarmId IS NOT NULL;";
		
		try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
			stmt.setString(1, logGroup);
			
			try (ResultSet set = stmt.executeQuery()) {
				return LogAlarmDataMapper.mapResultSetToLogAlarmData(set);
			}
		} catch (SQLException ex) {
			throw new InternalError("Error while querying database", ex);
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
			throw new InternalError("Error while closing connection to database");
		}
	}
}
