package com.ccc.logs.notifications;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


/**
 * Converts the custom fields object of {@code Optional<KeywordDataList>} to
 * a JSON object when deserializing
 */
class NullableKeywordDataListSerializer extends StdSerializer<Optional<KeywordDataList>> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 1653478956735934522L;
	
	/**
	 * Empty Constructor
	 */
	NullableKeywordDataListSerializer() {
		this(null);
	}
	
	/**
	 * Class constructor
	 * @param type Class object
	 * of the type to be converted to
	 */
	NullableKeywordDataListSerializer(Class<Optional<KeywordDataList>> type) {
		super(type);
	}

	@Override
	public void serialize(Optional<KeywordDataList> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (!value.isPresent()) {
			jgen.writeNull();
		}
		
		KeywordDataList keywordDataList = value.get();
		
		jgen.writeStartObject();
		
		jgen.writeArrayFieldStart("keywordList");
		for (KeywordData keyword : keywordDataList.getKeywordList()) {
			jgen.writeStartObject();
			
			jgen.writeNumberField("keywordId", keyword.getKeywordId());
			jgen.writeStringField("word", keyword.getWord());
			
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		
		jgen.writeStringField("relationship", keywordDataList.getRelationship());
		
		jgen.writeEndObject();
	}
}
