package com.future94.alarm.log.examples.spring.mvc.logback.bean;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.DefaultAlarmMessageContext;
import com.future94.alarm.log.common.dto.AlarmLogSimpleConfig;
import com.future94.alarm.log.common.dto.AlarmMailContent;

/**
 * @author weilai
 */
//@Component
public class CustomAlarmMessageContext2 extends DefaultAlarmMessageContext {

    @Override
    public String workWeixinContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return super.workWeixinContent(context, throwable, config);
    }

    @Override
    public String dingtalkContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return super.dingtalkContent(context, throwable, config);
    }

    @Override
    public AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
        return super.mailContent(context, throwable, config);
    }
}
