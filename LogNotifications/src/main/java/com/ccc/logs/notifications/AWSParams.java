package com.ccc.logs.notifications;

/**
 * Holds certain variables needed to connecting to
 * the database and configuring the clients for
 * AWS services. These values should be stored in the
 * environment variables of the Lambda function. The
 * access key id and secret access key are
 * stored by default on all AWS Lambda functions for
 * all AWS accounts
 */
class AWSParams {
	/**
	 * URL of the database that contains user information
	 * and data on the log alarms. This value must be entered
	 * manually into the Lambda function's environment
	 * variables
	 */
	public static final String DATABASE_URL = System.getenv("DATABASE_URL");
	
	/**
	 * Username of the database that contains user
	 * information and data on the log alarms. This
	 * value must be entered manually into the Lambda
	 * function's environment variables
	 */
	public static final String DATABASE_USERNAME = System.getenv("DATABASE_USERNAME");
	
	/**
	 * Password of the database that contains user
	 * information and data on the log alarms. This
	 * value must be entered manually into the Lambda
	 * function's environment variables
	 */
	public static final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
	
	/**
	 * The region of the AWS account that runs the Lambda
	 * function. This value is stored by default into
	 * the Lambda function's environment variables
	 */
	public static final String REGION = System.getenv("AWS_REGION");
}
