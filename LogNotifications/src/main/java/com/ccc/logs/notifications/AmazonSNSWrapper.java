package com.ccc.logs.notifications;

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
	private static final AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder
			.standard()
			.withCredentials(GlobalVariables.AWS_CREDENTIALS)
			.withRegion(GlobalVariables.AWS_REGION)
			.build();
	
	/**
	 * Suppresses default constructor
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
		PublishResult result = AmazonSNSWrapper.SNS_CLIENT.publish(request);
		
		return result;
	}
	
	/**
	 * Publishes a message to the specified SNS Topic ARN,
	 * along with the log group and log stream that the log
	 * message was put in
	 * @param snsTopicArn The ARN of the SNS Topic to be published to
	 * @param message The log message to be sent to be published
	 * @param logGroup The log group to be published
	 * @param logStream The log stream to be published
	 * @return
	 */
	public static PublishResult publishToSNS(String snsTopicArn, String message, String logGroup, String logStream) {
		String str = String.format("Log Group: %s\nLog Stream: %s\n%s", logGroup, logStream, message);
		PublishRequest request = new PublishRequest(snsTopicArn, str);
		PublishResult result = AmazonSNSWrapper.SNS_CLIENT.publish(request);
		
		return result;
	}
}
