package com.ccc.logs.notifications;

/**
 * Exception indicating that some exception occurred
 * within this Lambda function. This exception will be
 * be at the top of the stack trace whenever an exception
 * occurs in this lambda function
 */
class LogNotificationException extends RuntimeException {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 1251396239684590609L;
	
	/**
	 * Constructs a {@code LogNotificationException} with only a message
	 * @param message The message for this exception
	 */
	public LogNotificationException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a {@code LogNotificationException} with a message
	 * and a cause
	 * @param message The message for this exception
	 * @param cause The exception that caused this exception
	 */
	public LogNotificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
