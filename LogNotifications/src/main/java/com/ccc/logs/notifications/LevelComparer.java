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
		throw new LogNotificationError("No instances of LevelComparer should be made");
	}
	
	/**
	 * Compares a given log's level
	 * to certain log level criteria
	 * @param levelOfLogMessage The level of the
	 * given log
	 * @param logLevelData Contains info on how the
	 * level of the log alarm is to be compared to
	 * the level of the log message
	 * @return <tt>true</tt> if the
	 * given log's level meets the given
	 * criteria, <tt>false</tt> otherwise
	 */
	public static boolean compare(LogLevel levelOfLogMessage, LogLevelData logLevelData) {
		switch (logLevelData.getComparison()) {
		case "==":
			return levelOfLogMessage == logLevelData.getLevel();
		case "<":
			return levelOfLogMessage.ordinal() < logLevelData.getLevel().ordinal();
		case ">":
			return levelOfLogMessage.ordinal() > logLevelData.getLevel().ordinal();
		case "<=":
			return levelOfLogMessage.ordinal() <= logLevelData.getLevel().ordinal();
		case ">=":
			return levelOfLogMessage.ordinal() >= logLevelData.getLevel().ordinal();
		default:
			throw new LogNotificationError("Error while comparing log levels");
		}
	}
}
