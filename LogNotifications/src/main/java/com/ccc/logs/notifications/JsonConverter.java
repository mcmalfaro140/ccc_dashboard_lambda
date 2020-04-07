package com.ccc.logs.notifications;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

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
	 * This static-initialization block can be used to
	 * configure the {@code ObjectMapper} object
	 */
	static {
		JsonConverter.MAPPER.registerModule(new Jdk8Module());
	}
	
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
	 * Converts the {@code String} to the type
	 * specified by the given {@code Class} object
	 * @param <T> The type to convert the {@code String}
	 * to
	 * @param json The {@code String} containing the
	 * JSON data to be parsed
	 * @param type The {@code Class} object of the type to
	 * convert the JSON data to
	 * @return A POJO of the type contained in the {@code Class}
	 * object with the same data contained in the {@code String}
	 */
	public static <T> T parse(String json, Class<T> type) {
		try {
			return JsonConverter.MAPPER.readValue(json, type);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing JSON log data", ex);
		}
	}
	
	/**
	 * Converts data contained in a {@code JsonParser}
	 * to a {@code JsonNode}
	 * @param parser The {@code JsonParser} to be converted
	 * @return A {@code JsonNode} containing the same data
	 * as the given {@code JsonParser}
	 */
	public static JsonNode parse(JsonParser parser) {
		try {
			return JsonConverter.MAPPER.readTree(parser);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing custom field data", ex);
		}
	}
	
	/**
	 * Converts the {@code JsonParser} to the type
	 * specified by the given {@code Class} object
	 * @param <T> The type to convert the {@code JsonParser}
	 * to
	 * @param parser The {@code JsonParser} containing the
	 * JSON data to be parsed
	 * @param type The {@code Class} object of the type to
	 * convert the JSON data to
	 * @return A POJO of the type contained in the {@code Class}
	 * object with the same data contained in the {@code JsonParser}
	 */
	public static <T> T parse(JsonParser parser, Class<T> type) {
		try {
			return JsonConverter.MAPPER.readValue(parser, type);
		} catch (IOException ex) {
			throw new LogNotificationException("Error while parsing JSON log data", ex);
		}
	}
}
