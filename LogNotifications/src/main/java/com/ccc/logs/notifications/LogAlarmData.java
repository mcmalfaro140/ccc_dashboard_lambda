package com.ccc.logs.notifications;

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
	 * The data on the SNS Topics that should have messages
	 * sent to them if this log alarm is triggered
	 */
	private SNSTopicData[] snsTopicDataList;
	
	/**
	 * The list of keywords. All of these must be
	 * found in a given log message for this log alarm to
	 * be triggered. If there are no keywords to search for,
	 * this value will be <tt>null</tt>
	 */
	private String[] keywordList;
	
	/**
	 * The criteria that a given log's level must meet to
	 * trigger this log alarm
	 */
	private LogLevelCriteriaData logLevelCriteriaData;
	
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
			SNSTopicData[] snsTopicDataList,
			String[] keywordList,
			LogLevelCriteriaData logLevelCriteriaData
	) {
		this.logAlarmId = logAlarmId;
		this.snsTopicDataList = snsTopicDataList;
		this.logLevelCriteriaData = logLevelCriteriaData;
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
	 * Returns the list of keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 * @return The list of keywords to be searched for when
	 * determining whether or not a given log should
	 * trigger this log alarm
	 */
	public String[] getKeywordList() {
		return this.keywordList;
	}
	
	/**
	 * Returns the criteria that a given log's level must
	 * meet to trigger this log alarm
	 * @return The criteria that a given log's level must
	 * meet to trigger this log alarm
	 */
	public LogLevelCriteriaData getLogLevelCriteriaData() {
		return this.logLevelCriteriaData;
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
		return JsonParser.instance().prettyStringify(this);
	}
}
