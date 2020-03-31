package com.ccc.logs.notifications.backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The only controller containing the
 * only endpoint for this fake backend
 */
@RestController
public class HomeController {
	/**
	 * The logger that logs to the console and to
	 * AWS CloudWatch
	 */
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    /**
     * URL endpoint that logs to AWS CloudWatch
     * Run the app and go to "localhost:5050/" to do logging
     * @return An arbitrary string. Its value
     * does not matter
     */
    @GetMapping("/")
    public String hello() {
    	MDC.put("testKey", "testValue");
    	MDC.put("thing", "stuff");
    	this.logger.error("message0");
        this.logger.warn("message1");
        MDC.remove("testKey");
        this.logger.info("message2,message3");
        this.logger.debug("message4");
        this.logger.trace("message5");
        MDC.remove("testKey");
        
        return "Hello World in Spring Boot";
    }
}
