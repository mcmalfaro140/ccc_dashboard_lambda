package com.ccc.logs.notifications;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Singleton that converts POJOs to/from
 * JSON strings
 */
class JsonConverter {
	/**
	 * Singleton instance of this class
	 */
	private static final JsonConverter INSTANCE = new JsonConverter();
	
	/**
	 * The underlying object that actually does conversions
	 * to/from JSON strings
	 */
	private final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Empty constructor. The "mapper" field of type
	 * {@code ObjectMapper} may be configured here
	 */
	private JsonConverter() {
	}
	
	/**
	 * Global access point for the singleton instance
	 * @return
	 */
	public static JsonConverter instance() {
		return JsonConverter.INSTANCE;
	}
	
	/**
	 * Converts a POJO to a JSON string
	 * @param obj The object to be converted
	 * to a JSON string
	 * @return A JSON string representation
	 * of the provided object
	 */
	public String stringify(Object obj) {
		try {
			return this.mapper.writeValueAsString(obj);
		} catch (JsonProcessingException ex) {
			throw new LogNotificationException("Error while converting object to JSON string", ex);
		}
	}
	
	/**
	 * Converts a POJO to a pretty printed
	 * JSON string
	 * @param obj The object to be converted
	 * to a pretty printed JSON string
	 * @return A pretty printed JSON string
	 * representation of the provided object
	 */
	public String prettyStringify(Object obj) {
		try {
			return this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException ex) {
			throw new LogNotificationException("Error while converting object to pretty JSON string", ex);
		}
	}
	
	/**
	 * Converts a JSON string to a POJO
	 * @param <T> The type of object to
	 * be returned
	 * @param json The JSON string to be
	 * parsed
	 * @param type The class object of the
	 * type of object to be returned
	 * @return A POJO with the values
	 * specified in the JSON string
	 */
	public <T> T parse(String json, Class<T> type) {
		try {
			return this.mapper.readValue(json, type);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing JSON log data", ex);
		}
	}
}
