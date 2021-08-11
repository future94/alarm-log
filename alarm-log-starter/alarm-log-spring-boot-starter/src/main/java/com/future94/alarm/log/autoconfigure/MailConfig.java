package com.future94.alarm.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weilai
 */
@Data
@ConfigurationProperties(prefix = "spring.alarm-log.warn.mail")
public class MailConfig {

    private String smtpHost;

    private String smtpPort;

    private String to;

    private String from;

    private String username;

    private String password;

    private Boolean ssl = true;

    private Boolean debug = false;
}
