package com.future94.alarm.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weilai
 */
@Data
@ConfigurationProperties(prefix = "spring.alarm-log.warn.dingtalk")
public class DingtalkConfig {

    private String token;

    private String secret;
}
