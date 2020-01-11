package edu.csula.ccc.ccctest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class LogEvent {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private JsonNode jsonRepresentation;
	private String stringRepresentation;
	
	private LogEvent(JsonNode logEvent) {
		this.stringRepresentation = logEvent.get("message").asText();
		
		try {
			this.jsonRepresentation = LogEvent.MAPPER.readTree(this.stringRepresentation);
		} catch (IOException e) {
			throw new InternalError("Failed to parse JSON", e);
		}
	}
	
	public static List<LogEvent> extractLogEvents(String logAsJson) {
		List<LogEvent> logEventList = new ArrayList<LogEvent>();
		
		try {
			JsonNode logEventListAsJson = LogEvent.MAPPER.readTree(logAsJson).get("logEvents");
			
			for (JsonNode logEventAsJson : logEventListAsJson) {
				LogEvent logEvent = new LogEvent(logEventAsJson);
				logEventList.add(logEvent);
			}
		} catch (IOException e) {
			throw new InternalError("Failed to parse JSON", e);
		}
		
		return logEventList;
	}
	
	public JsonNode getJsonRepresentation() {
		return this.jsonRepresentation;
	}
	
	public String getStringRepresentation() {
		return this.stringRepresentation;
	}
	
	public String getLevel() {
		return this.jsonRepresentation.get("level").asText();
	}
}
