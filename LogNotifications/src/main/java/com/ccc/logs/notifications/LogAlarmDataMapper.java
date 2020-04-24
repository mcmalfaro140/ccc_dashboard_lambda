package com.ccc.logs.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Class container for functions that convert a 
 * {@code ResultSet} to the <tt>LogAlarmData</tt> objects
 */
class LogAlarmDataMapper {
	/**
	 * Suppresses default constructor
	 */
	private LogAlarmDataMapper() {
		throw new LogNotificationException("No instances of LogAlarmDataMapper should be made");
	}
	
	/**
	 * Converts the {@code ResultSet} to
	 * a {@code LogAlarmData} object
	 * @param set The {@code ResultSet}
	 * to be converted
	 * @return A List of <tt>LogAlarmData</tt>
	 * that corresponds to the log alarms listed
	 * in the given {@code ResultSet}
	 * @throws SQLException Thrown if there is an exception
	 * extracting the data from the {@code ResultSet}
	 */
	public static LinkedList<LogAlarmData> mapResultSet(ResultSet set) throws SQLException {
		LinkedList<LogAlarmData> logAlarmList = new LinkedList<LogAlarmData>();
		
		while (set.next()) {
			LogLevelData logLevelData = LogAlarmDataMapper._extractLogLevelData(set);
			Optional<KeywordData> keywordData = LogAlarmDataMapper._extractKeywordList(set);
			String[] snsTopicArnList = set.getString("TopicArns").split(",");
			
			logAlarmList.add(new LogAlarmData(snsTopicArnList, logLevelData, keywordData));
		}
		
		return logAlarmList;
	}
	
	/**
	 * Extracts the information regarding how to analyze log levels
	 * @param set The {@code ResultSet} to have data
	 * extracted from it
	 * @return The data describing how to analyze log levels
	 * @throws SQLException Thrown if there is an exception
	 * extracting the data from the {@code ResultSet}
	 */
	private static LogLevelData _extractLogLevelData(ResultSet set) throws SQLException {
		LogLevel logLevel = LogLevel.valueOf(set.getString("LogLevel"));
		String comparison = set.getString("Comparison");
		
		return new LogLevelData(logLevel, comparison);
	}
	
	/**
	 * Extracts the data on the keywords for the current
	 * row of the {@code ResultSet}
	 * @param set The {@code ResultSet} to have data
	 * extracted from it
	 * @return The data on the keywords for the current row
	 * of the {@code ResultSet}, <tt>null</tt> if there are
	 * no keywords to search for
	 * @throws SQLException Thrown if there is an exception
	 * extracting the data from the {@code ResultSet}
	 */
	private static Optional<KeywordData> _extractKeywordList(ResultSet set) throws SQLException {
		Optional<String> keywords = Optional.ofNullable(set.getString("Keywords"));
		Optional<String> relationship = Optional.ofNullable(set.getString("KeywordRelationship"));
		Optional<KeywordData> keywordData = 
				keywords.isPresent() ?
				Optional.of(new KeywordData(keywords.get().split(","), relationship.get())) :
				Optional.empty();
				
		return keywordData;
	}
}
