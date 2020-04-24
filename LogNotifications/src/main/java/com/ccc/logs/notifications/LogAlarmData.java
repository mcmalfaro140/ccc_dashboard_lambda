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
	 * The data on the SNS Topics that should have messages
	 * sent to them if this log alarm is triggered
	 */
	private String[] snsTopicArnList;
	
	/**
	 * Contains info on how to analyze the levels of the logs
	 * currently being processed
	 */
	private LogLevelData logLevelData;
	
	/**
	 * The data on what keywords should be searched for and
	 * whether or not all or none need to be found
	 */
	private Optional<KeywordData> keywordData;
	
	/**
	 * Constructs a log alarm
	 * @param snsTopicDataList The Amazon SNS Topics to
	 * send messages to if this log alarm is triggered
	 * @param logLevelData Contains info on how to analyze
	 * logs levels
	 * @param keywordList The list of keywords to search
	 * for when determining whether or not to trigger
	 * this log alarm
	 * alarm
	 */
	public LogAlarmData(String[] snsTopicArnList, LogLevelData logLevelData, Optional<KeywordData> keywordData) {
		this.logLevelData = logLevelData;
		this.snsTopicArnList = snsTopicArnList;
		this.keywordData = keywordData;
	}
	
	/**
	 * Returns the Amazon SNS Topics to be published to
	 * if this log alarm is triggered
	 * @return The Amazon SNS Topics to be published to
	 * if this log alarm is triggered
	 */
	public String[] getSNSTopicArnList() {
		return this.snsTopicArnList;
	}
	
	/**
	 * Returns the data on the keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 * @return The data on the keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 */
	public Optional<KeywordData> getKeywordData() {
		return this.keywordData;
	}
	
	/**
	 * Returns info on how to analyze log levels
	 * @return Info on how to analyze log levels
	 */
	public LogLevelData getLogLevelData() {
		return this.logLevelData;
	}
	
	/**
	 * Returns a string representation of this object
	 * as a pretty printed JSON string. Used solely for
	 * testing purposes
	 * @return A string representation of this object
	 * as a pretty printed JSON string
	 */
	@Override
	public String toString() {
		return JsonConverter.prettyStringify(this);
	}
}
