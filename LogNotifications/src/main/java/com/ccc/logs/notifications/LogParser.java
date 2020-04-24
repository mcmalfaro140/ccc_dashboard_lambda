package com.ccc.logs.notifications;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Responsible for decompressing and parsing the log data into a form
 * that can be read easily. The log data is the main input to the
 * Lambda function
 */
class LogParser {
	/**
	 * Suppresses default constructor
	 */
	private LogParser() {
		throw new LogNotificationException("No instances of LogParser should be made");
	}
	
	/**
	 * Decompresses and parses the main
	 * @param input The log data to this Lambda function
	 * @return The decompressed and parsed log data to
	 * the Lambda function
	 */
	public static LogData parse(Map<String, Map<String, String>> input) {
		String awslogs = LogParser._decodeInput(input);
		LogData data = JsonConverter.parse(awslogs, LogData.class);
		
		return data;
	}
	
	/**
	 * Decompresses the input to the Lambda function
	 * @param input The data to be decompressed
	 * @return The decompressed input
	 */
	private static String _decodeInput(Map<String, Map<String, String>> input) {
		String awsLogData = input.get("awslogs").get("data");
		byte[] compressed = awsLogData.getBytes(StandardCharsets.US_ASCII);
		byte[] decodedBytes = Base64.getDecoder().decode(compressed);
		
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes)) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.US_ASCII)) {
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
			throw new LogNotificationException("Failed to decompress content", e);
		}
	}
}
