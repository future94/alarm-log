package com.future94.alarm.log.autoconfigure;

import com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory;
import com.future94.alarm.log.warn.dingtalk.DingtalkWarnService;
import com.future94.alarm.log.warn.workweixin.WorkWeixinWarnService;
import com.future94.alarm.log.common.cache.AlarmLogContext;
import com.future94.alarm.log.warn.mail.MailWarnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author weilai
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AlarmLogConfig.class)
public class AlarmLogAutoConfiguration {

    @Autowired
    void setAlarmLogConfig(AlarmLogConfig alarmLogConfig) {
        Optional.ofNullable(alarmLogConfig.getDoWarnException()).ifPresent(AlarmLogContext::addDoWarnExceptionList);
    }

    @Configuration
    @ConditionalOnProperty(name = "spring.alarm-log.warn.mail.enabled", havingValue = "true")
    @EnableConfigurationProperties(MailConfig.class)
    static class MailWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(MailWarnService.class)
        public MailWarnService mailWarnService(final MailConfig mailConfig) {
            MailWarnService mailWarnService = new MailWarnService(mailConfig.getSmtpHost(), mailConfig.getSmtpPort(), mailConfig.getTo(), mailConfig.getFrom(), mailConfig.getUsername(), mailConfig.getPassword());
            mailWarnService.setSsl(mailConfig.getSsl());
            mailWarnService.setDebug(mailConfig.getDebug());
            return mailWarnService;
        }

        @Autowired
        void setDataChangedListener(MailWarnService mailWarnService) {
            AlarmLogWarnServiceFactory.setAlarmLogWarnService(mailWarnService);
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "spring.alarm-log.warn.workweixin.enabled", havingValue = "true")
    @EnableConfigurationProperties(WorkWeixinConfig.class)
    static class WorkWeixinWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(MailWarnService.class)
        public WorkWeixinWarnService workWeixinWarnService(final WorkWeixinConfig workWeixinConfig) {
            return new WorkWeixinWarnService(workWeixinConfig.getTo(), workWeixinConfig.getApplicationId(), workWeixinConfig.getCorpid(), workWeixinConfig.getCorpsecret());
        }

        @Autowired
        void setDataChangedListener(WorkWeixinWarnService workWeixinWarnService) {
            AlarmLogWarnServiceFactory.setAlarmLogWarnService(workWeixinWarnService);
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "spring.alarm-log.warn.dingtalk.enabled", havingValue = "true")
    @EnableConfigurationProperties(DingtalkConfig.class)
    static class DingtalkWarnServiceMethod {

        @Bean
        @ConditionalOnMissingBean(DingtalkWarnService.class)
        public DingtalkWarnService dingtalkWarnService(final DingtalkConfig dingtalkConfig) {
            return new DingtalkWarnService(dingtalkConfig.getToken(), dingtalkConfig.getSecret());
        }

        @Autowired
        void setDataChangedListener(DingtalkWarnService dingtalkWarnService) {
            AlarmLogWarnServiceFactory.setAlarmLogWarnService(dingtalkWarnService);
        }
    }
}
