package com.ccc.logs.notifications;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Converts a timestamp that is represented as a string
 * value to some kind of date object
 */
class TimestampAsStringDeserializer extends StdDeserializer<ZonedDateTime> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -528618308905715473L;
	
	/**
	 * Empty constructor
	 */
	TimestampAsStringDeserializer() {
		this(null);
	}
	
	/**
	 * Class constructor
	 * @param type Class object of
	 * the type to be deserialized
	 */
	TimestampAsStringDeserializer(Class<ZonedDateTime> type) {
		super(type);
	}

	@Override
	public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		String epochTimeAsString = parser.getText();
		long epochTimeAsLong = Long.parseLong(epochTimeAsString);
		ZonedDateTime date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTimeAsLong), ZoneId.systemDefault());
		
		return date;
	}
}