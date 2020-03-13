package com.ccc.lambda;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

class AmazonSNSWrapper {
	private static AmazonSNS SNS_CLIENT;
	
	static {
		AWSCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
		
		AmazonSNSWrapper.SNS_CLIENT = AmazonSNSClientBuilder
				.standard()
				.withCredentials(credentialsProvider)
				.withRegion(AwsParams.REGION)
				.build();
	}
	
	private AmazonSNSWrapper() {
	}
	
	public static void publishToSNS(String snsTopicArn, String message) {
		AmazonSNSWrapper.SNS_CLIENT.publish(snsTopicArn, message);
	}
}
