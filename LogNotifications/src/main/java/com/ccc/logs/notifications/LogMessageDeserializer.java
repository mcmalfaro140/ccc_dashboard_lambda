package com.ccc.logs.notifications;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * All log messages are initially stored as JSON strings.
 * This class parses the JSON string that corresponds to
 * the log message
 */
class LogMessageDeserializer extends StdDeserializer<LogMessage> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -480265028502567136L;
	
	/**
	 * Empty constructor
	 */
	LogMessageDeserializer() {
		this(null);
	}
	
	/**
	 * Class constructor
	 * @param type Class object of
	 * the type to be deserialized
	 */
	LogMessageDeserializer(Class<LogMessage> type) {
		super(type);
	}

	@Override
	public LogMessage deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		return JsonConverter.parse(parser, LogMessage.class);
	}
}
