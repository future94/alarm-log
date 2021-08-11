package com.future94.alarm.log.examples.spring.mvc.log4j.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weilai
 */
@RestController
public class TestController {

    private Logger logger = Logger.getLogger(TestController.class);

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
            throw new RuntimeException("error");
        }
    }
}
