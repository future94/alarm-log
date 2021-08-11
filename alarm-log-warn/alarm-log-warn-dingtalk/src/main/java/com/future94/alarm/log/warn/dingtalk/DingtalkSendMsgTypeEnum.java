package com.future94.alarm.log.warn.dingtalk;

import lombok.Getter;

/**
 * @author weilai
 */
@Getter
public enum DingtalkSendMsgTypeEnum {

    /**
     * text
     */
    TEXT("text"),
    ;

    private String type;

    DingtalkSendMsgTypeEnum(String type) {
        this.type = type;
    }
}
