package com.ccc.lambda;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

class LogData {
	private String messageType;
	private String owner;
	private String logGroup;
	private String logStream;
	private List<String> subscriptionFilters;
	private List<LogEvent> logEvents;
	
	public String getMessageType() {
		return this.messageType;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getLogGroup() {
		return this.logGroup;
	}
	
	public String getLogStream() {
		return this.logStream;
	}
	
	public List<String> getSubscriptionFilters() {
		return this.subscriptionFilters;
	}
	
	public List<LogEvent> getLogEvents() {
		return this.logEvents;
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
