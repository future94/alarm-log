package com.future94.alarm.log.warn.common;

import com.future94.alarm.log.common.dto.AlarmInfoContext;

/**
 * @author weilai
 */
public interface AlarmLogWarnService {

    boolean send(AlarmInfoContext context, Throwable throwable);
}
