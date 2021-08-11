package com.future94.alarm.log.warn.common;

/**
 * @author weilai
 */
public interface AlarmLogWarnService {

    boolean send(Throwable throwable);
}
