package com.ccc.logs.notifications;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
	 * The time that this log event occurred
	 */
	@JsonSerialize(using=TimestampSerializer.class)
	@JsonDeserialize(using=TimestampAsLongDeserializer.class)
	private ZonedDateTime timestamp;
	
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
	 * Returns the time this log event
	 * occurred
	 * @return The time this log event
	 * occurred
	 */
	public ZonedDateTime getTimestamp() {
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
		return JsonConverter.instance().prettyStringify(this);
	}
}