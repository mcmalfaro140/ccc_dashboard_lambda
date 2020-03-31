package com.ccc.logs.notifications;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Converts the custom fields object of {@code LogMessage} to
 * a JSON object when deserializing
 */
class NullableJsonNodeSerializer extends StdSerializer<Optional<JsonNode>> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -8001427317815074915L;
	
	/**
	 * Empty Constructor
	 */
	NullableJsonNodeSerializer() {
		this(null);
	}
	
	/** Class constructor
	 * @param type The class object
	 * of the type being converted to
	 * a JSON object
	 */
	NullableJsonNodeSerializer(Class<Optional<JsonNode>> type) {
		super(type);
	}
	
	/**
	 * Converts the {@code JsonNode} representing the custom fields
	 * to a normal JSON object
	 */
	@Override
	public void serialize(Optional<JsonNode> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (value.isPresent()) {
			jgen.writeStartObject();
			
			Iterator<Entry<String, JsonNode>> fieldIter = value.get().fields();
			
			do {
				Entry<String, JsonNode> field = fieldIter.next();
				
				String fieldName = field.getKey();
				JsonNode element = field.getValue();
				
				switch (element.getNodeType()) {
				case NULL:
					jgen.writeNullField(fieldName);
					break;
				case BOOLEAN:
					jgen.writeBooleanField(fieldName, element.asBoolean());
					break;
				case STRING:
					jgen.writeStringField(fieldName, element.asText());
					break;
				case NUMBER:
					if (element.isIntegralNumber()) {
						jgen.writeNumberField(fieldName, element.asInt());
					}
					else {
						jgen.writeNumberField(fieldName, element.asDouble());
					}
				
					break;
				default:
					throw new LogNotificationException("Unexpected type for custom field value");
				}
			} while (fieldIter.hasNext());
			
			jgen.writeEndObject();
		}
		else {
			jgen.writeNull();
		}
	}
}
