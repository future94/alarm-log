package com.future94.alarm.log.warn.workweixin;

import lombok.Data;

import java.util.Map;

/**
 * @author weilai
 */
@Data
public class WorkWeixinSendParam {

    private String touser;

    private Integer agentid;

    private String msgtype;

    private Map<String, Object> text;
}
