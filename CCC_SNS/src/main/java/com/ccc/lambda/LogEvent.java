package com.ccc.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

class LogEvent {
	private String id;
	private long timestamp;
	@JsonDeserialize(converter=LogMessageConverter.class)
	private LogMessage message;
	
	public String getId() {
		return this.id;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public LogMessage getMessage() {
		return this.message;
	}
	
	@Override
	public String toString() {
		try {
			return ObjectMapperContainer.getJsonParser().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException ex) {
			throw new InternalError("Error while converting " + this + " to JSON");
		}
	}
}
