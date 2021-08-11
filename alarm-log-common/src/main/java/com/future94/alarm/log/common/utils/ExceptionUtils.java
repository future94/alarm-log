package com.future94.alarm.log.common.utils;

import com.future94.alarm.log.common.exception.AlarmLogDoWarnException;
import com.future94.alarm.log.common.exception.AlarmLogException;
import com.future94.alarm.log.common.exception.AlarmLogRuntimeException;

import java.util.List;

/**
 * @author weilai
 */
public class ExceptionUtils {

    public static boolean doWarnExceptionInstance(Throwable throwable) {
        return throwable instanceof AlarmLogDoWarnException
                || throwable instanceof AlarmLogException
                || throwable instanceof AlarmLogRuntimeException;
    }

    public static boolean doWarnExceptionName(Throwable warnExceptionClass, List<String> doWarnExceptionList) {
        return doWarnExceptionList.contains(warnExceptionClass.getClass().getName());
    }

    public static boolean doWarnExceptionExtend(Throwable warnExceptionClass, List<Class<? extends Throwable>> doExtendWarnExceptionList) {
        for (Class<?> aClass : doExtendWarnExceptionList) {
            if (aClass.isAssignableFrom(warnExceptionClass.getClass())) {
                return true;
            }
        }
        return false;
    }
}
