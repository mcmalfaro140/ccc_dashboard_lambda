package com.ccc.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

class LogMessage {	
	private String timestamp;
	private String level;
	private String thread;
	private String logger;
	private String message;
	private String context;
	private JsonNode mdc;
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public String getThread() {
		return this.thread;
	}
	
	public String getLogger() {
		return this.logger;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getContext() {
		return this.context;
	}
	
	public JsonNode getMDC() {
		return this.mdc;
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
