package edu.csula.ccc.ccctest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class LogEvent {
	private static final ObjectMapper MAPPER = new ObjectMapper();	
	private JsonNode _json;
	private String _string;
	
	private LogEvent(JsonNode logEvent) {
		this._string = logEvent.get("message").asText();
		
		try {
			this._json = LogEvent.MAPPER.readTree(this._string);
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
	
	public JsonNode getJson() {
		return this._json;
	}
	
	public String getString() {
		return this._string;
	}
	
	public String getLevel() {
		return this.getJson().get("level").asText();
	}
}
