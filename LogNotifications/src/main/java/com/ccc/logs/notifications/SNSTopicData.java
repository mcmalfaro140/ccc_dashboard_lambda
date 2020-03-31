package com.ccc.logs.notifications;

/**
 * Contains information on the Amazon SNS Topics
 * that are attached to log alarms
 */
class SNSTopicData {
	/**
	 * SQL ID for this Amazon SNS Topic
	 */
	private int snsTopicId;
	
	/**
	 * The Amazon SNS Topic ARN for this
	 * SNS Topic
	 */
	private String topicArn;
	
	/**
	 * Constructs an object that described
	 * an SNS Topic
	 * @param snsTopicId SQL ID for this
	 * Amazon SNS Topic
	 * @param topicArn The Topic ARN for this
	 * SNS Topic
	 */
	public SNSTopicData(int snsTopicId, String topicArn) {
		this.snsTopicId = snsTopicId;
		this.topicArn = topicArn;
	}
	
	/**
	 * Returns the SQL ID for this SNS Topic
	 * @return The SQL ID for this SNS Topic
	 */
	public int getSNSTopicId() {
		return this.snsTopicId;
	}
	
	/**
	 * Returns the SNS Topic ARN for this SNS
	 * Topic
	 * @return The SNS Topic ARN for this SNS
	 * Topic
	 */
	public String getTopicArn() {
		return this.topicArn;
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
