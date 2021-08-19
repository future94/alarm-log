package com.future94.alarm.log.warn.common;

import com.future94.alarm.log.common.dto.AlarmInfoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weilai
 */
public abstract class BaseWarnService implements AlarmLogWarnService {

    private final Logger logger = LoggerFactory.getLogger(BaseWarnService.class);

    @Override
    public boolean send(AlarmInfoContext context, Throwable throwable) {
        try {
            doSend(context, throwable);
            return true;
        } catch (Exception e) {
            logger.error("send warn message error", e);
            return false;
        }
    }

    protected abstract void doSend(AlarmInfoContext context, Throwable throwable) throws Exception;
}
