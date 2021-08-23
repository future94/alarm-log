package com.future94.alarm.log.examples.spring.mvc.logback.bean;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.AlarmLogContext;
import com.future94.alarm.log.common.context.AlarmMessageContext;
import com.future94.alarm.log.common.dto.AlarmLogSimpleConfig;
import com.future94.alarm.log.common.dto.AlarmMailContent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author weilai
 */
//@Component
public class CustomAlarmMessageContext1 implements AlarmMessageContext, InitializingBean {

    @Override
    public String workWeixinContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return context.getMessage();
    }

    @Override
    public String dingtalkContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return context.getMessage();
    }

    @Override
    public AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return new AlarmMailContent(context.getMessage(), context.getClassName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AlarmLogContext.setAlarmMessageContext(this);
    }
}
