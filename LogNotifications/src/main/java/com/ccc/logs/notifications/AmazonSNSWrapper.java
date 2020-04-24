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
	 * The subject for all messages sent by this Lambda
	 * function
	 */
	private static final String MESSAGE_SUBJECT = "CCC Dashboard Log Alarms";
	
	/**
	 * The first text to appear in all messages sent by this
	 * Lambda function
	 */
	private static final String MESSAGE_BODY_INTRO = "The following log was sent to CloudWatch:\n\n";
	
	/**
	 * Suppresses default constructor
	 */
	private AmazonSNSWrapper() {
		throw new LogNotificationError("No instances of AmazonSNSWrapper should be made");
	}
	
	/**
	 * Publishes a message to the specified SNS Topic ARN
	 * @param snsTopicArn The ARN of the SNS Topic to be published to
	 * @param message The message to be published
	 * @return The response object of the SNS publish request that is performed
	 */
	public static PublishResult publishToSNS(String snsTopicArn, String message) {
		String finalMessage = AmazonSNSWrapper.MESSAGE_BODY_INTRO + message;
			
		PublishRequest request = new PublishRequest(snsTopicArn, finalMessage, AmazonSNSWrapper.MESSAGE_SUBJECT);
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
		String finalMessage = String.format("%sLog Group: %s\nLog Stream: %s\n%s", AmazonSNSWrapper.MESSAGE_BODY_INTRO, logGroup, logStream, message);
		
		PublishRequest request = new PublishRequest(snsTopicArn, finalMessage, AmazonSNSWrapper.MESSAGE_SUBJECT);
		PublishResult result = AmazonSNSWrapper.SNS_CLIENT.publish(request);
		
		return result;
	}
}
