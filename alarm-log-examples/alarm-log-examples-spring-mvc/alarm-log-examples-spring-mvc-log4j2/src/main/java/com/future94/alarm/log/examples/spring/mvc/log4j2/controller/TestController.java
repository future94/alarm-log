package com.future94.alarm.log.examples.spring.mvc.log4j2.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weilai
 */
@RestController
public class TestController {

    private final Logger logger = LogManager.getLogger(TestController.class);

    @GetMapping("/")
    public String test() {
        return "success";
    }

    @GetMapping("/test")
    public String aa(Integer id) {
        if (id == 1) {
            return "success";
        } else {
            logger.error("1", new RuntimeException("123"));
            return "error";
        }
    }
}
