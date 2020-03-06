package com.ccc.api.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String hello() {
    	//MDC.put("testKey", "testValue");
        logger.error("An error message");
        logger.warn("A warning message");
        logger.info("An information message");
        logger.debug("A debugging message");
        logger.trace("A message for more detailed debugging");
        //MDC.remove("testKey");
        
        return "Hello World in Spring Boot xxx";
    }
}
