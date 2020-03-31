package com.ccc.logs.notifications;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Converts a JSON object or null value to an instance
 * of {@code Optional<JsonNode>}
 */
class NullableJsonNodeDeserializer extends StdDeserializer<Optional<JsonNode>> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 5237941070129277540L;
	
	/**
	 * Empty Constructor
	 */
	NullableJsonNodeDeserializer() {
		this(null);
	}
	
	/**
	 * {@code JavaType} constructor
	 * @param type The type object
	 * describing the type of object
	 * to be used
	 */
	NullableJsonNodeDeserializer(JavaType type) {
		super(type);
	}

	@Override
	public Optional<JsonNode> deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = JsonConverter.parse(parser);
		Optional<JsonNode> nullableNode = Optional.of(node);
		
		return nullableNode;
	}
}
