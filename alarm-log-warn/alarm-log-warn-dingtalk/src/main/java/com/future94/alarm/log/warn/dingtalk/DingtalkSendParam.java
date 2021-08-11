package com.future94.alarm.log.warn.dingtalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author weilai
 */
@Data
public class DingtalkSendParam {

    private String msgtype;

    private Text text;

    @Data
    @AllArgsConstructor
    public static class Text {

        private String content;
    }
}
