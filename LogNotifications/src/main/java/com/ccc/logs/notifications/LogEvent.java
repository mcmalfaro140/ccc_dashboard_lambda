package com.ccc.logs.notifications;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Contains information on what data triggered
 * the Lambda function
 */
class LogEvent {
	/**
	 * The generated unique ID for this log
	 * event
	 */
	private String id;
	
	/**
	 * The epoch time for this log event
	 */
	private long timestamp;
	
	/**
	 * The JSON formatted message that serves
	 * as the actual message for this log
	 * event
	 */
	@JsonDeserialize(converter=LogMessageConverter.class)
	private LogMessage message;
	
	/**
	 * Returns the generated unique ID for
	 * this log event
	 * @return The generated unique ID for
	 * this log event
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Returns the epoch time for this log
	 * event
	 * @return The epoch time for this log
	 * event
	 */
	public long getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Returns the JSON object that serves as
	 * the actual message for this log event
	 * @return The JSON object that serves as
	 * the actual message for this log event
	 */
	public LogMessage getMessage() {
		return this.message;
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
