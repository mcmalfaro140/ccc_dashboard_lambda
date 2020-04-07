package com.ccc.logs.notifications;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum LogLevel {
	TRACE,
	DEBUG,
	INFO,
	WARN,
	ERROR
}
