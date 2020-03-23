package com.ccc.logs.notifications;

import java.util.List;

/**
 * Contains information on all of the logs being
 * processed during this invocation of this AWS
 * Lambda function
 */
class LogData {
	/**
	 * The type of message
	 */
	private String messageType;
	
	/**
	 * An ID corresponding the AWS account running
	 * this Lambda function
	 */
	private String owner;
	
	/**
	 * The log group that contains the logs currently
	 * being processed for this invocation of this
	 * Lambda function
	 */
	private String logGroup;
	
	/**
	 * The log stream that contains the logs currently
	 * being processed for this invocation of this
	 * Lambda function
	 */
	private String logStream;
	
	/**
	 * The subscription filters that passed the filters
	 * set on AWS Lambda. This value will likely not be
	 * useful
	 */
	private List<String> subscriptionFilters;
	
	/**
	 * The list of log events being processed for this
	 * invocation of this Lambda function
	 */
	private List<LogEvent> logEvents;
	
	
	/**
	 * Returns the message type
	 * @return The message type
	 */
	public String getMessageType() {
		return this.messageType;
	}
	
	/**
	 * Returns the owner ID
	 * @return The owner ID
	 */
	public String getOwner() {
		return this.owner;
	}
	
	/**
	 * Returns the log group that contains
	 * the logs being processed for this
	 * invocation of this Lambda function
	 * @return The log group that contains
	 * the logs being processed for this
	 * invocation of this Lambda function
	 */
	public String getLogGroup() {
		return this.logGroup;
	}
	
	/**
	 * Returns the log stream that contains
	 * the logs being processed for this
	 * invocation of this Lambda function
	 * @return The log stream that contains
	 * the logs being processed for this
	 * invocation of this Lambda function
	 */
	public String getLogStream() {
		return this.logStream;
	}
	
	/**
	 * Returns the subscription filters that passed the filters
	 * set on AWS Lambda. This value will likely not be
	 * useful
	 * @return The subscription filters that passed the filters
	 * set on AWS Lambda
	 */
	public List<String> getSubscriptionFilters() {
		return this.subscriptionFilters;
	}
	
	/**
	 * Returns the list of log events being processed for this
	 * invocation of this Lambda function
	 * @return The list of log events being processed for this
	 * invocation of this Lambda function
	 */
	public List<LogEvent> getLogEvents() {
		return this.logEvents;
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
		return JsonConverter.instance().prettyStringify(this);
	}
}
