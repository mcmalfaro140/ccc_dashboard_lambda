package com.ccc.lambda;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class AmazonSNSWrapper {
	private static AmazonSNS SNS_CLIENT;
	
	static {
		AWSCredentials credentials = new BasicAWSCredentials(AwsParams.ACCESS_KEY_ID, AwsParams.SECRET_ACCESS_KEY);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		
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
