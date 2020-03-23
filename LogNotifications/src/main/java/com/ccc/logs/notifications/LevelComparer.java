package com.ccc.logs.notifications;

/**
 * Class container for comparing a given log's
 * level to certain log level criteria
 */
class LevelComparer {
	/**
	 * Used to make comparison of log levels easier
	 * by using ordinal values
	 */
	private enum LogLevel { TRACE, DEBUG, INFO, WARN, ERROR }
	
	/**
	 * Suppresses default constructor
	 */
	private LevelComparer() {
	}
	
	/**
	 * Compares a given log's level
	 * to certain log level criteria
	 * @param level The level of the
	 * given log
	 * @param logLevelCriteria The
	 * criteria to compare the given
	 * log's level to
	 * @return <tt>true</tt> if the
	 * given log's level meets the given
	 * criteria, <tt>false</tt> otherwise
	 */
	public static boolean compare(String level, LogLevelCriteriaData logLevelCriteria) {
		LogLevel levelOfThreshold = LogLevel.valueOf(logLevelCriteria.getLogLevel());		
		LogLevel levelOfLog = LogLevel.valueOf(level);
		
		switch (logLevelCriteria.getComparison()) {
		case "==":
			return levelOfLog.equals(levelOfThreshold);
		case ">":
			return levelOfLog.ordinal() > levelOfThreshold.ordinal();
		case ">=":
			return levelOfLog.ordinal() >= levelOfThreshold.ordinal();
		default:
			throw new LogNotificationException("Error in log level criteria data");
		}
	}
}
