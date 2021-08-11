package com.future94.alarm.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weilai
 */
@Data
@ConfigurationProperties(prefix = "spring.alarm-log.warn.workweixin")
public class WorkWeixinConfig {

    private String to;

    private Integer applicationId;

    private String corpid;

    private String corpsecret;

}
