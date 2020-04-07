package com.ccc.logs.notifications;

/**
 * Class container for comparing a given log's
 * level to certain log level criteria
 */
class LevelComparer {	
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
	public static boolean compare(LogLevel levelOfLogMessage, LogLevel levelOfLogAlarm, String comparison) {		
		switch (comparison) {
		case "==":
			return levelOfLogMessage.equals(levelOfLogAlarm);
		case "<":
			return levelOfLogMessage.ordinal() < levelOfLogAlarm.ordinal();
		case ">":
			return levelOfLogMessage.ordinal() > levelOfLogAlarm.ordinal();
		case "<=":
			return levelOfLogMessage.ordinal() <= levelOfLogAlarm.ordinal();
		case ">=":
			return levelOfLogMessage.ordinal() >= levelOfLogAlarm.ordinal();
		default:
			throw new LogNotificationException("Error while comparing log levels");
		}
	}
}
