package com.ccc.logs.notifications;

import com.amazonaws.SDKGlobalConfiguration;

public class GlobalVariables {
	private GlobalVariables() {
	}
	
	/**
	 * The region of the AWS account running the Lambda
	 * function. Will automatically be set in the Lambda
	 * function's environment variables
	 */
	public static final String AWS_REGION = System.getenv(SDKGlobalConfiguration.AWS_REGION_ENV_VAR);
	
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
	 * If any exceptions occur during the execution of
	 * this Lambda function, A message will be send to
	 * this Amazon SNS Topic
	 */
	public static final String EXCEPTION_SNS_TOPIC_ARN = System.getenv("EXCEPTION_SNS_TOPIC_ARN");
	
	/**
	 * The time zone to be used for the timestamp fields in the
	 * log messages
	 */
	public static final String TIME_ZONE = "America/Los_Angeles";
}
