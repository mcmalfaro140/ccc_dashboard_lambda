package com.ccc.logs.notifications;

import java.util.Optional;

/**
 * Contains only the necessary information needed to check
 * if a given log should trigger this alarm and the Amazon
 * SNS Topic ARNs to which to send a message in the event
 * that that happens. All conditions must be met to trigger
 * a log alarm
 */
class LogAlarmData {
	/**
	 * SQL ID for the log alarm
	 */
	private int logAlarmId;
	
	/**
	 * Represent the level of log that
	 * the log messages are to be compared to
	 */
	private LogLevel logLevel;
	
	/**
	 * Represents how the level of the log messages
	 * are to be compared to the level of this log alarm
	 */
	private String comparison;
	
	/**
	 * The data on the SNS Topics that should have messages
	 * sent to them if this log alarm is triggered
	 */
	private SNSTopicData[] snsTopicDataList;
	
	/**
	 * The data on what keywords should be searched for and
	 * whether or not all or none need to be found
	 */
	private Optional<KeywordDataList> keywordDataList;
	
	/**
	 * Constructs a log alarm
	 * @param logAlarmId The SQL ID for this log alarm
	 * @param snsTopicDataList The Amazon SNS Topics to
	 * send messages to if this log alarm is triggered
	 * @param keywordList The list of keywords to search
	 * for when determining whether or not to trigger
	 * this log alarm
	 * @param logLevelCriteriaData The criteria a given
	 * log's level must meet in order to trigger this log
	 * alarm
	 */
	public LogAlarmData(
			int logAlarmId,
			LogLevel logLevel,
			String comparison,
			SNSTopicData[] snsTopicDataList,
			Optional<KeywordDataList> keywordDataList
	) {
		this.logAlarmId = logAlarmId;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.snsTopicDataList = snsTopicDataList;
		this.keywordDataList = keywordDataList;
	}
	
	/**
	 * Returns the SQL ID of this log alarm
	 * @return The SQL ID of this log alarm
	 */
	public int getLogAlarmId() {
		return this.logAlarmId;
	}
	
	/**
	 * Returns the Amazon SNS Topics to be published to
	 * if this log alarm is triggered
	 * @return The Amazon SNS Topics to be published to
	 * if this log alarm is triggered
	 */
	public SNSTopicData[] getSNSTopicDataList() {
		return this.snsTopicDataList;
	}
	
	/**
	 * Returns the data on the keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 * @return The data on the keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 */
	public Optional<KeywordDataList> getKeywordDataList() {
		return this.keywordDataList;
	}
	
	/**
	 * Returns the level of this log alarm
	 * @return The level of this log alarm
	 */
	public LogLevel getLogLevel() {
		return this.logLevel;
	}
	
	/**
	 * Returns the way log messages are to be compared to this log alarm
	 * @return The way log messages are to be compared to this log alarm
	 */
	public String getComparison() {
		return this.comparison;
	}
	
	/**
	 * Returns a string representation of this log alarm
	 * as a pretty printed JSON string. Used solely for
	 * testing purposes
	 * @return A string representation of this log alarm
	 * as a pretty printed JSON string
	 */
	@Override
	public String toString() {
		return JsonConverter.prettyStringify(this);
	}
}
