package com.ccc.lambda;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.zip.GZIPInputStream;

class LogParser {	
	private LogParser() {
	}
	
	public static LogData parse(Object input) {
		try {
			String awslogs = LogParser._decodeInput(input);
			LogData data = ObjectMapperContainer.getJsonParser().readValue(awslogs, LogData.class);
			
			return data;
		} catch (IOException ex) {
			throw new InternalError("Error while parsing JSON", ex);
		}
	}
	
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
