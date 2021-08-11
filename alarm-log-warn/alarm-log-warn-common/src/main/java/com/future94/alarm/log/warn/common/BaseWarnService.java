package com.future94.alarm.log.warn.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weilai
 */
public abstract class BaseWarnService implements AlarmLogWarnService {

    private final Logger logger = LoggerFactory.getLogger(BaseWarnService.class);

    @Override
    public boolean send(Throwable throwable) {
        try {
            doSend(throwable);
            return true;
        } catch (Exception e) {
            logger.error("send warn message error", e);
            return false;
        }
    }

    protected abstract void doSend(Throwable throwable) throws Exception;
}
