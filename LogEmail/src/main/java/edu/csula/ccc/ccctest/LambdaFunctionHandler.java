package edu.csula.ccc.ccctest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {
	private static final AwsParams PARAMS = AwsParams.getInstance();
	private static final AWSCredentials CREDENTIALS = new BasicAWSCredentials(PARAMS.getAccessKeyId(), PARAMS.getSecretAccessKey());
	private static final AWSCredentialsProvider CREDENTIALS_PROVIDER = new AWSStaticCredentialsProvider(CREDENTIALS);
	private static final AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard().withRegion(PARAMS.getRegion()).withCredentials(CREDENTIALS_PROVIDER).build();
	
	@Override
    public String handleRequest(Object input, Context context) {
		final String decodedInput = this.decodeInput(input);
		final PublishRequest request = new PublishRequest(PARAMS.getTopicArn(), decodedInput);
		final PublishResult result = SNS_CLIENT.publish(request);
		
		final LambdaLogger logger = context.getLogger();
		logger.log("Message Id: " + result.getMessageId());
		logger.log("Input: " + decodedInput);
		
		//Map<String, ?> map = (Map<String, ?>)input;

        return "Hello from Lambda!";
    }
	
	private String decodeInput(Object input) {
		return input.toString();
	}
}
