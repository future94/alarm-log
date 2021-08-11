package com.future94.alarm.log.aspect;

import com.future94.alarm.log.common.cache.AlarmLogContext;
import com.future94.alarm.log.common.thread.AlarmLogThreadFactory;
import com.future94.alarm.log.common.utils.ExceptionUtils;
import com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weilai
 */
@Aspect
public class AlarmAspect {

    public static final String POINTCUT_SIGN =
            "@within(com.future94.alarm.log.aspect.Alarm) || @annotation(com.future94.alarm.log.aspect.Alarm)";

    private ExecutorService executorService = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), AlarmLogThreadFactory.create("alarm-log-aspect-", false));

    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {

    }

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void doRetryProcess(JoinPoint joinPoint, Throwable ex) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Alarm alarm = signature.getMethod().getAnnotation(Alarm.class);
        if (Objects.isNull(alarm)) {
            alarm = signature.getMethod().getDeclaringClass().getAnnotation(Alarm.class);
        }
        Class<? extends Throwable>[] doExtendWarnExceptionClasses = alarm.doWarnException();
        boolean doWarnProcess;
        if (alarm.warnExceptionExtend()) {
            doWarnProcess = ExceptionUtils.doWarnExceptionExtend(ex, Arrays.asList(doExtendWarnExceptionClasses));
        } else {
            List<String> doWarnExceptionList = new ArrayList<>(doExtendWarnExceptionClasses.length);
            for (Class<? extends Throwable> exceptionClass : doExtendWarnExceptionClasses) {
                doWarnExceptionList.add(exceptionClass.getName());
            }
            doWarnProcess = ExceptionUtils.doWarnExceptionName(ex, doWarnExceptionList);
        }
        if (doWarnProcess
                || AlarmLogContext.doWarnException(ex)
                || ExceptionUtils.doWarnExceptionInstance(ex)) {
            executorService.execute(() -> AlarmLogWarnServiceFactory.getServiceList().forEach(alarmLogWarnService -> alarmLogWarnService.send(ex)));
        }
        throw ex;
    }
}
