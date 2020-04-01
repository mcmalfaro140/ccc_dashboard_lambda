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
 * Converts a timestamp that is represented as a long
 * value to some kind of date object
 */
class TimestampAsLongDeserializer extends StdDeserializer<ZonedDateTime> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -528618308905715473L;
	
	/**
	 * Empty constructor
	 */
	TimestampAsLongDeserializer() {
		this(null);
	}
	
	/**
	 * Class constructor
	 * @param type Class object of
	 * the type to be deserialized
	 */
	TimestampAsLongDeserializer(Class<ZonedDateTime> type) {
		super(type);
	}
	
	@Override
	public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		long epochTime = parser.getLongValue(); 
		ZonedDateTime date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneId.systemDefault());
		
		return date;
	}
}
