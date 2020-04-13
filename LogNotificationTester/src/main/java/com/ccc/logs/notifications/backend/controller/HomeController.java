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
     * Run the app and go to "localhost:6000/" to do logging
     * @return An arbitrary string. Its value
     * does not matter
     */
    @GetMapping(path="/test1", produces="text/plain")
    public String test1() {
    	MDC.put("testKey", "testValue");
    	MDC.put("thing", "stuff");
    	this.logger.error("message0");
        this.logger.warn("message1");
        MDC.remove("testKey");
        this.logger.info("message2,message3");
        
        return "Test1";
    }
    
    @GetMapping(path="/test2", produces="text/plain")
    public String test2() {
    	MDC.put("1", "2");
    	MDC.put("3", "4");
    	this.logger.error("Hello");
    	MDC.remove("1");
    	this.logger.warn("Cheese");
    	MDC.remove("3");
    	this.logger.info("T-Rex");
    	
    	return "Test2";
    }
}
