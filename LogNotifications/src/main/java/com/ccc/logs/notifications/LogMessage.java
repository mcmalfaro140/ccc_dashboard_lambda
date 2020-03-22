package com.ccc.logs.notifications;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The message attached to a log event. All log messages are
 * initially formatted as pretty printed JSON strings that are
 * parsed and converted to POJOs
 */
class LogMessage {
	/**
	 * The epoch time 
	 * for this log message
	 */
	private String timestamp;
	
	/**
	 * The level for this
	 * log message
	 */
	private String level;
	
	/**
	 * The thread that recorded
	 * this log message
	 */
	private String thread;
	
	/**
	 * The logger that logged
	 * this log message
	 */
	private String logger;
	
	/**
	 * The information conveying
	 * what happened that caused
	 * logging to be done
	 */
	private String message;
	
	/**
	 * The context for this log
	 */
	private String context;
	
	/**
	 * JSON object that contains
	 * all custom fields for this
	 * log message. If there are no
	 * custom fields, then this value
	 * is <tt>null</tt>
	 */
	private JsonNode mdc;
	
	/**
	 * Returns the epoch time for
	 * this log message
	 * @return The epoch time for
	 * this log message
	 */
	public String getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Returns the level of this
	 * log message
	 * @return The level of this
	 * log message
	 */
	public String getLevel() {
		return this.level;
	}
	
	/**
	 * The thread that recorded
	 * this log message
	 * @return The thread that
	 * recored this log message
	 */
	public String getThread() {
		return this.thread;
	}
	
	/**
	 * The logger that logged this
	 * log message
	 * @return The logger that logged
	 * this log message
	 */
	public String getLogger() {
		return this.logger;
	}
	
	/**
	 * Returns the information conveying
	 * what happened that caused
	 * logging to be done
	 * @return The information conveying
	 * what happened that caused
	 * logging to be done
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Returns the context of this log
	 * message
	 * @return The context of this log
	 * message
	 */
	public String getContext() {
		return this.context;
	}
	
	/**
	 * Returns a JSON object that contains
	 * any custom fields attached to this
	 * log message
	 * @return A JSON object that contains
	 * any custom fields attached to this
	 * log message
	 */
	public JsonNode getMDC() {
		return this.mdc;
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