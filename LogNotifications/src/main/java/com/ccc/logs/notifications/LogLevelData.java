package com.ccc.logs.notifications;

/**
 * Contains information describing
 * how to analyze log levels
 */
class LogLevelData {
	/**
	 * The log level to compare other
	 * log levels to
	 */
	private LogLevel level;
	
	/**
	 * Describes how other log levels are
	 * to be compared to this one
	 */
	private String comparison;
	
	/**
	 * Constructs an object representing
	 * log level data
	 * @param logLevel The log level to compare
	 * other log levels to
	 * @param comparison How to compare other log
	 * levels to this one
	 */
	public LogLevelData(LogLevel level, String comparison) {
		this.level = level;
		this.comparison = comparison;
	}
	
	/**
	 * Returns the log level
	 * @return The log level
	 */
	public LogLevel getLevel() {
		return this.level;
	}
	
	/**
	 * Returns how other log levels are to be
	 * compared to the one stored here
	 * @return How other log levels are to be
	 * compared to the one stored here
	 */
	public String getComparison() {
		return this.comparison;
	}
}
