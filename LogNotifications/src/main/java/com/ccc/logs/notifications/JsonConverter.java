package com.ccc.logs.notifications;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Singleton that converts POJOs to/from
 * JSON strings
 */
class JsonConverter {	
	/**
	 * The underlying object that actually does conversions
	 * to/from JSON strings
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * Suppresses default constructor
	 */
	private JsonConverter() {
	}
	
	/**
	 * Converts a POJO to a JSON string
	 * @param obj The object to be converted
	 * to a JSON string
	 * @return A JSON string representation
	 * of the provided object
	 */
	public static String stringify(Object obj) {
		try {
			return JsonConverter.MAPPER.writeValueAsString(obj);
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
	public static String prettyStringify(Object obj) {
		try {
			return JsonConverter.MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
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
	public static <T> T parse(String json, Class<T> type) {
		try {
			return JsonConverter.MAPPER.readValue(json, type);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing JSON log data", ex);
		}
	}
	
	public static JsonNode parse(JsonParser parser) {
		try {
			return JsonConverter.MAPPER.readTree(parser);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing custom field data", ex);
		}
	}
}
