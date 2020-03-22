package com.ccc.logs.notifications;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * Handles the Amazon Simple Notification
 * Service (SNS) client object
 */
class AmazonSNSWrapper {
	/**
	 * The Amazon SNS client object
	 */
	private static final AmazonSNS SNS_CLIENT =  AmazonSNSClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion(SDKGlobalConfiguration.AWS_REGION_ENV_VAR)
			.build();
	
	/**
	 * Serves only to suppress the default constructor
	 */
	private AmazonSNSWrapper() {
	}
	
	/**
	 * Publishes a message to the specified SNS Topic ARN
	 * @param snsTopicArn The ARN of the SNS Topic to be published to
	 * @param message The message to be published
	 * @return The response object of the SNS publish request that is performed
	 */
	public static PublishResult publishToSNS(String snsTopicArn, String message) {
		PublishRequest request = new PublishRequest(snsTopicArn, message);
		
		return AmazonSNSWrapper.SNS_CLIENT.publish(request);
	}
}
