package com.ccc.logs.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Class container for functions that convert a 
 * <tt>ResultSet</tt> to the <tt>LogAlarmData</tt> objects
 */
class LogAlarmDataMapper {
	/**
	 * Suppresses default constructor
	 */
	private LogAlarmDataMapper() {
	}
	
	/**
	 * Converts the <tt>ResultSet</tt> to
	 * a <tt>LogAlarmData</tt> POJO
	 * @param set The <tt>ResultSet</tt>
	 * to be converted
	 * @return A List of <tt>LogAlarmData</tt>
	 * that corresponds to the log alarms listed
	 * in the given <tt>ResultSet</tt>
	 */
	public static LinkedList<LogAlarmData> mapResultSetToLogAlarmData(ResultSet set) {
		LinkedList<LogAlarmData> logAlarmList = new LinkedList<LogAlarmData>();
		
		try {
			while (set.next()) {				
				int logAlarmId = set.getInt("LogAlarmId");
				
				if (0 == logAlarmId) {
					return logAlarmList;
				}
				
				@Nullable KeywordDataList keywordData = LogAlarmDataMapper._extractKeywordList(set);
				SNSTopicData[] snsTopicDataList = LogAlarmDataMapper._extractSNSTopicData(set);
				LogLevelCriteriaData logLevelCriteriaData = LogAlarmDataMapper._extractLogLevelCriteriaData(set);
				
				logAlarmList.add(new LogAlarmData(logAlarmId, snsTopicDataList, keywordData, logLevelCriteriaData));
			}
			
			return logAlarmList;
		} catch (SQLException ex) {
			throw new LogNotificationException("Error while iterating through ResultSet of data on log alarms", ex);
		}
	}
	
	/**
	 * Extracts the data on the keywords for the current
	 * row of the <tt>ResultSet</tt>
	 * @param set The <tt>ResultSet</tt> to have data
	 * extracted from it
	 * @return The data on the keywords for the current row
	 * of the <tt>ResultSet</tt>, <tt>null</tt> if there are
	 * no keywords to search for
	 * @throws SQLException Thrown if there is an exception
	 * extracting the keywords from the <tt>ResultSet</tt>
	 */
	private static @Nullable KeywordDataList _extractKeywordList(ResultSet set) throws SQLException {
		String keywordIds = set.getString("KeywordIds");
		@Nullable String keywords = set.getString("Keywords");
		String relationship = set.getString("KeywordRelationship");
		
		if (null == keywords) {
			return null;
		}
		else {
			int[] keywordIdList = LogAlarmDataMapper._makeArrayOfIds(keywordIds);
			String[] keywordList = keywords.split(",");
			
			LinkedList<KeywordData> keywordData = new LinkedList<KeywordData>();
			
			for (int index = 0; index < keywordList.length; ++index) {
				keywordData.add(new KeywordData(keywordIdList[index], keywordList[index]));
			}
			
			return new KeywordDataList(keywordData, relationship);
		}
	}
	
	/**
	 * Extracts the data on the Amazon SNS Topics in the current row
	 * of the <tt>ResultSet</tt>
	 * @param set The <tt>ResultSet</tt> to have data extracted
	 * from it
	 * @return The list of data on the Amazon SNS Topics in the current
	 * row of the <tt>ResultSet</tt>
	 * @throws SQLException Thrown if there is an exception
	 * extracting the keywords from the <tt>ResultSet</tt>
	 */
	private static SNSTopicData[] _extractSNSTopicData(ResultSet set) throws SQLException {
		String snsTopicIds = set.getString("SNSTopicIds");
		String topicArns = set.getString("TopicArns");
		
		int[] snsTopicIdList = LogAlarmDataMapper._makeArrayOfIds(snsTopicIds);
		String[] topicArnList = topicArns.split(",");
		
		return LogAlarmDataMapper._makeSNSTopicDataList(snsTopicIdList, topicArnList);
	}
	
	/**
	 * Converts a string of SQL IDs for Amazon SNS Topics
	 * separated by commas to an array on <tt>ints</tt>
	 * @param snsTopicIds The string of Amazon SNS Topic
	 * SQL IDs separated by commas
	 * @return An array of <tt>ints</tt> corresponding to
	 * the SQL IDs of Amazon SNS Topics listed in the
	 * "snsTopicIds" parameter
	 */
	private static int[] _makeArrayOfIds(String str) {
		String[] stringArray = str.split(",");
		int[] intArray = new int[stringArray.length];
		
		for (int index = 0; index < stringArray.length; ++index) {
			intArray[index] = Integer.parseInt(stringArray[index]);
		}
		
		return intArray;
	}
	
	/**
	 * Constructing the array of data on Amazon SNS Topics from
	 * the two arrays of SQL IDs of Amazon SNS Topics and Topic ARNs
	 * @param snsTopicIdList An array of SQL IDs for the Amazon
	 * SNS Topics
	 * @param topicArnList An array of Amazon SNS Topic ARNs
	 * @return An array of data on Amazon SNS Topics which describe
	 * some Amazon SNS Topics
	 */
	private static SNSTopicData[] _makeSNSTopicDataList(int[] snsTopicIdList, String[] topicArnList) {
		SNSTopicData[] snsTopicDataList = new SNSTopicData[topicArnList.length];
		
		for (int index = 0; index < topicArnList.length; ++index) {
			snsTopicDataList[index] = new SNSTopicData(snsTopicIdList[index], topicArnList[index]);
		}
		
		return snsTopicDataList;
	}
	
	/**
	 * Extracts the log level criteria from the current row of the
	 * <tt>ResultSet</tt>
	 * @param set The <tt>ResultSet</tt> to have data extracted
	 * from it
	 * @return The log level criteria in the current row of the
	 * <tt>ResultSet</tt>
	 * @throws SQLException Thrown if there is an exception extracting
	 * the log level criteria from the <tt>ResultSet</tt>
	 */
	private static LogLevelCriteriaData _extractLogLevelCriteriaData(ResultSet set) throws SQLException {
		int logLevelCriteriaId = set.getInt("LogLevelCriteriaId");
		String logLevel = set.getString("LogLevel");
		String criteria = set.getString("Comparison");
		
		return new LogLevelCriteriaData(logLevelCriteriaId, logLevel, criteria);
	}
}
