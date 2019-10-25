package edu.csula.ccc.ccctest;

import java.util.Map;
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
	private static final String REGION = "us-east-1";
	private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:112911356528:TestTopic";
	private static final String ACCESS_KEY_ID = "AKIAI5H4Y2GDQWXMQJMQ";
	private static final String SECRET_ACCESS_KEY = "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";
	private static final AWSCredentials CREDENTIALS = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
	private static final AWSCredentialsProvider CREDENTIALS_PROVIDER = new AWSStaticCredentialsProvider(CREDENTIALS);
	private static final AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard().withRegion(REGION).withCredentials(CREDENTIALS_PROVIDER).build();
	
	@Override
    public String handleRequest(Object input, Context context) {
		final String decodedInput = this.decodeInput(input);
		final PublishRequest request = new PublishRequest(TOPIC_ARN, decodedInput);
		final PublishResult result = SNS_CLIENT.publish(request);
		
		final LambdaLogger logger = context.getLogger();
		logger.log("Message Id: " + result.getMessageId());
		logger.log("Input: " + decodedInput);
		
		Map<String, ?> map = (Map<String, ?>)input;
			
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			logger.log(entry.getKey().getClass().toString());
			logger.log(entry.getValue().getClass().toString());
		}

        return "Hello from Lambda!";
    }
	
	private String decodeInput(Object input) {
		return input.toString();
	}
}
