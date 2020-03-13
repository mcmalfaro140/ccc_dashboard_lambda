package com.ccc.lambda;

class AwsParams {
	public static final String DATABASE_URL = System.getenv("DATABASE_URL");
	public static final String DATABASE_USERNAME = System.getenv("DATABASE_USERNAME");
	public static final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
	public static final String REGION = System.getenv("AWS_REGION");
}
