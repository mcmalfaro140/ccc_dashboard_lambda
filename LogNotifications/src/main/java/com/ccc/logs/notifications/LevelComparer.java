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
	 * @param levelOfLogMessage The level of the
	 * given log
	 * @param levelOfLogAlarm The level to compare
	 * the given log to
	 * @param comparison The method of comparing log levels
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
