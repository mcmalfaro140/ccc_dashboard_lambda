package edu.csula.ccc.ccctest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {
	@Override
    public String handleRequest(Object input, Context context) {
		String decodedInput = this.decodeInput(input);
		List<LogEvent> logEventList = LogEvent.extractLogEvents(decodedInput);
		this.sendMessage(logEventList);

        return "";
    }
	
	private String decodeInput(Object input) {
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)input;
		String awsData = map.get("awslogs").get("data");
		byte[] compressed = awsData.getBytes(StandardCharsets.US_ASCII);
		byte[] decodedBytes = Base64.getDecoder().decode(compressed);
		
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes)) {
			try (GZIPInputStream compressorInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(compressorInputStream, StandardCharsets.US_ASCII)) {
					try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
						StringBuilder output = new StringBuilder();
						
						for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
							output.append(line);
						}
						
						return output.toString();
					}
				}
			}
		} catch (IOException e) {
			throw new InternalError("Failed to decompress content", e);
		}
	}
	
	private void sendMessage(List<LogEvent> logEventList) {
		AWSCredentials credentials = new BasicAWSCredentials(AwsParams.ACCESS_KEY_ID, AwsParams.SECRET_ACCESS_KEY);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		AmazonSNS snsClient = AmazonSNSClientBuilder
				.standard()
				.withRegion(AwsParams.REGION)
				.withCredentials(credentialsProvider)
				.build();
		
		for (LogEvent logEvent : logEventList) {
			String level = logEvent.getLevel();
			
			if (level.equals("WARN")) {
				PublishRequest request = new PublishRequest(AwsParams.WARN_TOPIC_ARN, logEvent.getStringRepresentation());
				snsClient.publish(request);
			}
			else if (level.equals("ERROR")) {
				PublishRequest request = new PublishRequest(AwsParams.ERROR_TOPIC_ARN, logEvent.getStringRepresentation());
				snsClient.publish(request);
			}
		}
	}
}
