package com.future94.alarm.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author weilai
 */
@Data
@ConfigurationProperties(prefix = "spring.alarm-log")
public class AlarmLogConfig {

    private Integer maxRetryTimes = 3;

    private Integer retrySleepMillis = 1000;

    private Boolean printStackTrace = false;

    private Boolean simpleWarnInfo = false;

    private Boolean warnExceptionExtend = false;

    private List<String> doWarnException;
}
