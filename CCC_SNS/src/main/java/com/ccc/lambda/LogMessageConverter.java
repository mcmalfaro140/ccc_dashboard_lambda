package com.ccc.lambda;

import java.io.IOException;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LogMessageConverter extends StdConverter<String, LogMessage> {	
	private LogMessageConverter() {
	}
	
	@Override
	public LogMessage convert(String messageAsString) {
		try {
			return ObjectMapperContainer.getJsonParser().readValue(messageAsString, LogMessage.class);
		} catch (IOException ex) {
			throw new InternalError("Error while parsing log message", ex);
		}
	}
}
