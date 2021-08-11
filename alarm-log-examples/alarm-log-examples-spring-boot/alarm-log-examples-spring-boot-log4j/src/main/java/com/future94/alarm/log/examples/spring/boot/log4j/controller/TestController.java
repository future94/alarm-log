package com.future94.alarm.log.examples.spring.boot.log4j.controller;

import com.future94.alarm.log.aspect.Alarm;
import com.future94.alarm.log.examples.spring.boot.log4j.exception.TestAspectException;
import com.future94.alarm.log.examples.spring.boot.log4j.exception.TestExtendsException;
import com.future94.alarm.log.examples.spring.boot.log4j.exception.TestExtendsRuntimeException;
import com.future94.alarm.log.examples.spring.boot.log4j.exception.TestImplException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weilai
 */
@RestController
public class TestController {

    private static Logger logger = LogManager.getLogger(TestController.class);

    @GetMapping("/test1")
    public void test1() {
        logger.error("test1", new TestImplException());
    }

    @GetMapping("/test2")
    public void test2() throws TestExtendsException {
        logger.error("test2", new TestExtendsException());
    }

    @GetMapping("/test3")
    public void test3() {
        logger.error("test3", new TestExtendsRuntimeException());
    }

    @GetMapping("/test4")
    @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
    public void test4() {
        logger.error("test4", new TestAspectException());
    }
}