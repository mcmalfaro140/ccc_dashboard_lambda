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
    	MDC.put("testKey", "testValue");
        logger.error("message0");
        logger.warn("message1");
        logger.info("message2");
        logger.debug("message3");
        logger.trace("message4");
        MDC.remove("testKey");
        
        return "Hello World in Spring Boot xxx";
    }
}
