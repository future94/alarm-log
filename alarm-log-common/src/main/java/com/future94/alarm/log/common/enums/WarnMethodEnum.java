package com.future94.alarm.log.common.enums;

import lombok.Getter;

/**
 * @author weilai
 */
@Getter
public enum WarnMethodEnum {
    /**
     * mail
     */
    MAIL("com.future94.alarm-log.warn.mail.MailWarnService"),

    /**
     * https://www.dingtalk.com/
     */
    DINGTALK("com.future94.alarm.log.warn.dingtalk.DingtalkWarnService"),

    /**
     * https://work.weixin.qq.com/
     */
    WORKWEIXIN("com.future94.alarm-log.warn.workweixin.WorkWeixinWarnService"),
    ;

    private String className;

    WarnMethodEnum(String className) {
        this.className = className;
    }
}
