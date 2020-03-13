package com.ccc.lambda;

import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectMapperContainer {
	private static final ObjectMapper JSON_PARSER = new ObjectMapper();
	
	private ObjectMapperContainer() {
	}
	
	public static ObjectMapper getJsonParser() {
		return ObjectMapperContainer.JSON_PARSER;
	}
}
