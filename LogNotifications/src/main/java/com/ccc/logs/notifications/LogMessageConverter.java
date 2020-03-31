package com.ccc.logs.notifications;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * All log messages are initially stored as JSON strings.
 * This class parses the JSON string that corresponds to
 * the log message
 */
class LogMessageConverter extends StdConverter<String, LogMessage> {
	/**
	 * Converts the log message, which is initially stored
	 * as a JSON formatted string to a POJO
	 * @param messageAsString The JSON formatted string to
	 * be converted to a POJO
	 * @return An instance of <tt>LogMessage</tt> with the
	 * same fields and values for those fields as the provided
	 * JSON formated string
	 */
	@Override
	public LogMessage convert(String messageAsString) {
		return JsonConverter.parse(messageAsString, LogMessage.class);
	}
}
