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
	 * Suppresses the default constructor
	 */
	private LogParser() {
	}
	
	/**
	 * Decompresses and parses the main
	 * @param input The main input (in other words, log data)
	 * to our Lambda function
	 * @return The decompressed and parsed log data that was
	 * input to our Lambda function
	 */
	public static LogData parse(Object input) {
		String awslogs = LogParser._decodeInput(input);
		LogData data = JsonParser.instance().parse(awslogs, LogData.class);
		
		return data;
	}
	
	/**
	 * Decompresses the input to out Lambda function
	 * @param input The data to be decompressed
	 * @return The decompressed input
	 */
	private static String _decodeInput(Object input) {
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
}