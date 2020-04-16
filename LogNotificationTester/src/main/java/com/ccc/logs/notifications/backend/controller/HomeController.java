package com.ccc.logs.notifications.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @GetMapping(path="/error1", produces="text/plain")
    public String test1() {
    	this.logger.error("message1");
        
        return "Test1";
    }
    
    @GetMapping(path="/errorNull", produces="text/plain")
    public String errorNull() {
    	this.logger.error("error messsage containing null");
        
        return "Test3";
    }
    
    @GetMapping(path="/warn", produces="text/plain")
    public String test2() {
    	this.logger.warn("Hello");
    	this.logger.warn("Cheese");
    	this.logger.warn("new null");
    	
    	return "Test2";
    }
}
