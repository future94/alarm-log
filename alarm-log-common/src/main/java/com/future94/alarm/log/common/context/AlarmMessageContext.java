package com.future94.alarm.log.common.context;

import com.future94.alarm.log.common.dto.AlarmLogSimpleConfig;
import com.future94.alarm.log.common.dto.AlarmMailContent;

/**
 * @author weilai
 */
public interface AlarmMessageContext {

    /**
     * Customize the content sent to work weixin.
     * @param context   The alarm log info.
     * @param throwable     The throwable that was caught.
     * @param config    The config context.
     * @return Content sent to work weixin.
     */
    String workWeixinContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config);

    /**
     * Customize the content sent to ding talk.
     * @param context   The alarm log info.
     * @param throwable     The throwable that was caught.
     * @param config    The config context.
     * @return Content sent to ding talk.
     */
    String dingtalkContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config);

    /**
     * Customize the content sent to mail.
     * @param context   The alarm log info.
     * @param throwable     The throwable that was caught.
     * @param config    The config context.
     * @return Content sent to mail.
     */
    AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config);
}
