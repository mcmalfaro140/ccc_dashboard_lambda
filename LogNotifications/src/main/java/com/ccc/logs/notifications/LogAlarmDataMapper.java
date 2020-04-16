package com.ccc.logs.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

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
	 * Converts the {@code ResultSet} to
	 * a {@code LogAlarmData} object
	 * @param set The <tt>ResultSet</tt>
	 * to be converted
	 * @return A List of <tt>LogAlarmData</tt>
	 * that corresponds to the log alarms listed
	 * in the given {@code ResultSet}
	 */
	public static LinkedList<LogAlarmData> mapResultSet(ResultSet set) throws SQLException {
		LinkedList<LogAlarmData> logAlarmList = new LinkedList<LogAlarmData>();
		
		while (set.next()) {				
			Long logAlarmId = set.getLong("LogAlarmId");
			
			if (0 == logAlarmId) {
				return logAlarmList;
			}
			
			LogLevel logLevel = LogLevel.valueOf(set.getString("LogLevel"));
			String comparison = set.getString("Comparison");
			Optional<KeywordDataList> keywordData = LogAlarmDataMapper._extractKeywordList(set);
			SNSTopicData[] snsTopicDataList = LogAlarmDataMapper._extractSNSTopicData(set);
			
			logAlarmList.add(new LogAlarmData(logAlarmId, logLevel, comparison, snsTopicDataList, keywordData));
		}
		
		return logAlarmList;
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
	private static Optional<KeywordDataList> _extractKeywordList(ResultSet set) throws SQLException {
		String keywordIds = set.getString("KeywordIds");
		Optional<String> keywords = Optional.ofNullable(set.getString("Keywords"));
		Optional<String> relationship = Optional.ofNullable(set.getString("KeywordRelationship"));
		
		if (keywords.isPresent()) {
			Long[] keywordIdList = LogAlarmDataMapper._makeArrayOfIds(keywordIds);
			String[] keywordList = keywords.get().split(",");
			
			LinkedList<KeywordData> keywordData = new LinkedList<KeywordData>();
			
			for (int index = 0; index < keywordList.length; ++index) {
				keywordData.add(new KeywordData(keywordIdList[index], keywordList[index]));
			}
			
			return Optional.of(new KeywordDataList(keywordData, relationship.get()));
		}
		
		return Optional.empty();
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
		
		Long[] snsTopicIdList = LogAlarmDataMapper._makeArrayOfIds(snsTopicIds);
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
	private static Long[] _makeArrayOfIds(String str) {
		String[] stringArray = str.split(",");
		Long[] intArray = new Long[stringArray.length];
		
		for (int index = 0; index < stringArray.length; ++index) {
			intArray[index] = Long.parseLong(stringArray[index]);
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
	private static SNSTopicData[] _makeSNSTopicDataList(Long[] snsTopicIdList, String[] topicArnList) {
		SNSTopicData[] snsTopicDataList = new SNSTopicData[topicArnList.length];
		
		for (int index = 0; index < topicArnList.length; ++index) {
			snsTopicDataList[index] = new SNSTopicData(snsTopicIdList[index], topicArnList[index]);
		}
		
		return snsTopicDataList;
	}
}
