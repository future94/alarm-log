package com.future94.alarm.log.warn.common;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.AlarmLogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weilai
 */
public abstract class BaseWarnService implements AlarmLogWarnService {

    private final Logger logger = LoggerFactory.getLogger(BaseWarnService.class);

    private int maxRetryTimes;

    private int retrySleepMillis;

    public BaseWarnService() {
        this(AlarmLogContext.getMaxRetryTimes(), AlarmLogContext.getRetrySleepMillis());
    }

    public BaseWarnService(int maxRetryTimes, int retrySleepMillis) {
        this.maxRetryTimes = maxRetryTimes;
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public boolean send(AlarmInfoContext context, Throwable throwable) {
        int retryTimes = 0;
        do {
            try {
                doSend(context, throwable);
                return true;
            } catch (Exception e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    return false;
                }
                int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                logger.warn("send warn message error, retry the {} time after {} ms", retryTimes + 1, sleepMillis);
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);
        return false;
    }

    protected abstract void doSend(AlarmInfoContext context, Throwable throwable) throws Exception;
}
