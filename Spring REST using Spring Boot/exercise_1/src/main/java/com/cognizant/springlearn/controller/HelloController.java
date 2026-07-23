package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello World RESTful Web Service
 * Method: GET
 * URL: /hello
 * Sample Request : http://localhost:8083/hello
 * Sample Response: Hello World!!
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("START - sayHello()");

        String response = "Hello World!!";

        logger.info("END - sayHello()");
        return response;
    }
}
