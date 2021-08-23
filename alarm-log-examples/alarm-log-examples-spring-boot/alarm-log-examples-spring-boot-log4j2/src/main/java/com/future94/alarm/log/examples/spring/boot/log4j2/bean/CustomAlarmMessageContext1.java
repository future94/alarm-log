package com.future94.alarm.log.examples.spring.boot.log4j2.bean;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.AlarmMessageContext;
import com.future94.alarm.log.common.dto.AlarmLogSimpleConfig;
import com.future94.alarm.log.common.dto.AlarmMailContent;

/**
 * @author weilai
 */
//@Component
public class CustomAlarmMessageContext1 implements AlarmMessageContext {

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
}
