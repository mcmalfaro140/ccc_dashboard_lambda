package com.ccc.logs.notifications;

/**
 * Contains information on the criteria
 * a given log's level must meet in order
 * to trigger a specific log alarm
 */
class LogLevelCriteriaData {
	/**
	 * SQL ID for this log level
	 * criteria
	 */
	private int logLevelCriteriaId;
	
	/**
	 * The specific log level that
	 * serves as the threshold for comparing
	 * logs' levels to log alarms.
	 * Possible values are "TRACE",
	 * "DEBUG", "INFO", "WARN",
	 * "ERROR"
	 */
	private String logLevel;
	
	/**
	 * Specifies how the threshold
	 * specified in the "logLevel"
	 * field is to be compared to
	 * any given log's level.
	 * Possible values are "==",
	 * ">", ">="
	 */
	private String comparison;
	
	/**
	 * Constructs an object that
	 * describes log level criteria
	 * @param logLevelCriteriaId SQL ID for
	 * this log level criteria
	 * @param logLevel The specific log level that
	 * serves as the threshold for comparing
	 * logs' levels to log alarms.
	 * Possible values are "TRACE",
	 * "DEBUG", "INFO", "WARN",
	 * "ERROR"
	 * @param criteria Specifies how the threshold
	 * specified in the "logLevel"
	 * field is to be compared to
	 * any given log's level.
	 * Possible values are "==",
	 * ">", ">="
	 */
	public LogLevelCriteriaData(int logLevelCriteriaId, String logLevel, String comparison) {
		this.logLevelCriteriaId = logLevelCriteriaId;
		this.logLevel = logLevel;
		this.comparison = comparison;
	}
	
	/**
	 * Returns the SQL ID of this log
	 * level criteria
	 * @return The SQL ID of this log
	 * level criteria
	 */
	public int getLogLevelCriteriaId() {
		return this.logLevelCriteriaId;
	}
	
	/**
	 * Returns the log level that 
	 * serves the threshold for comparing
	 * logs' levels to log alarms.
	 * @return "TRACE", "DEBUG",
	 * "INFO", "WARN", or "ERROR"
	 */
	public String getLogLevel() {
		return this.logLevel;
	}
	
	/**
	 * Returns a string specifying
	 * how the threshold detailed in
	 * the "logLevel" field is to be
	 * compared to the level of any
	 * given log
	 * @return A string specifying
	 * how the threshold detailed in
	 * the "logLevel" field is to be
	 * compared to the level of any
	 * given log
	 */
	public String getComparison() {
		return this.comparison;
	}
	
	/**
	 * Returns a string representation of this log alarm
	 * as a pretty printed JSON string. Used solely for
	 * testing purposes
	 * @return A string representation of this log alarm
	 * as a pretty printed JSON string
	 */
	@Override
	public String toString() {
		return JsonConverter.instance().prettyStringify(this);
	}
}
