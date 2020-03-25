package com.ccc.log.notification.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starting point for the fake backend that tests
 * the Lambda function
 */
@SpringBootApplication
public class LogMessagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogMessagerApplication.class, args);
	}
}
