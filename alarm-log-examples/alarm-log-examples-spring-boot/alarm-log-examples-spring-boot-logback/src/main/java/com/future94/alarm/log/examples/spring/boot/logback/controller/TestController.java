package com.future94.alarm.log.examples.spring.boot.logback.controller;

import com.future94.alarm.log.aspect.Alarm;
import com.future94.alarm.log.examples.spring.boot.logback.exception.TestAspectException;
import com.future94.alarm.log.examples.spring.boot.logback.exception.TestExtendsException;
import com.future94.alarm.log.examples.spring.boot.logback.exception.TestExtendsRuntimeException;
import com.future94.alarm.log.examples.spring.boot.logback.exception.TestImplException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
//        logger.error("test1", new TestImplException());
        throw new TestImplException();
    }

    @GetMapping("/test2")
    public void test2() throws TestExtendsException {
        throw new TestExtendsException();
    }

    @GetMapping("/test3")
    public void test3() {
        throw new TestExtendsRuntimeException();
    }

    @GetMapping("/test4")
    @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
    public void test4() {
        throw new TestAspectException();
    }
}