package com.future94.alarm.log.core.enhance.logback;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.future94.alarm.log.common.cache.AlarmLogContext;
import com.future94.alarm.log.common.utils.ExceptionUtils;
import com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author weilai
 */
public class AlarmLogLogbackAsyncAppender extends AsyncAppender {

    /**
     * @param doWarnException parsing xml doWarnException param
     */
    public void setDoWarnException(String doWarnException) {
        Optional.ofNullable(doWarnException).ifPresent(className -> AlarmLogContext.addDoWarnExceptionList(Arrays.asList(className.split(","))));
    }

    /**
     * @param warnExceptionExtend parsing xml warnExceptionExtend param
     */
    public void setWarnExceptionExtend(Boolean warnExceptionExtend) {
        Optional.ofNullable(warnExceptionExtend).ifPresent(AlarmLogContext::setWarnExceptionExtend);
    }

    @Override
    public void doAppend(ILoggingEvent eventObject) {
        if(eventObject instanceof LoggingEvent){
            LoggingEvent loggingEvent = (LoggingEvent)eventObject;
            ThrowableProxy throwableProxy = (ThrowableProxy) loggingEvent.getThrowableProxy();
            if (Objects.nonNull(throwableProxy)) {
                Throwable throwable = throwableProxy.getThrowable();
                if (AlarmLogContext.doWarnException(throwable)
                        || ExceptionUtils.doWarnExceptionInstance(throwable)) {
                    AlarmLogWarnServiceFactory.getServiceList().forEach(alarmLogWarnService -> alarmLogWarnService.send(throwable));
                }
            }
        }
        super.doAppend(eventObject);
    }
}
