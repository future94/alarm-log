package com.future94.alarm.log.aspect;

import com.future94.alarm.log.common.cache.AlarmLogContext;
import com.future94.alarm.log.common.dto.AlarmInfoContext;
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
        Alarm alarmMethod = signature.getMethod().getAnnotation(Alarm.class);
        Alarm alarmClass = signature.getMethod().getDeclaringClass().getAnnotation(Alarm.class);
        if (doWarnProcess(alarmMethod, ex)
                || doWarnProcess(alarmClass, ex)
                || AlarmLogContext.doWarnException(ex)
                || ExceptionUtils.doWarnExceptionInstance(ex)) {
            String threadName = Thread.currentThread().getName();
            StackTraceElement stackTraceElement = ex.getStackTrace()[0];
            executorService.execute(() -> AlarmLogWarnServiceFactory.getServiceList().forEach(alarmLogWarnService -> alarmLogWarnService.send(
                    AlarmInfoContext.builder()
                            .message(ex.getMessage())
                            .throwableName(ex.getClass().getName())
                            .loggerName(joinPoint.getSignature().getDeclaringTypeName())
                            .threadName(threadName)
                            .fileName(stackTraceElement.getFileName())
                            .lineNumber(stackTraceElement.getLineNumber())
                            .methodName(stackTraceElement.getMethodName())
                            .className(stackTraceElement.getClassName())
                            .build()
                    , ex)));
        }
        throw ex;
    }

    private boolean doWarnProcess(Alarm alarm, Throwable ex) {
        if (Objects.isNull(alarm)) {
            return false;
        }
        Class<? extends Throwable>[] doExtendWarnExceptionClasses = alarm.doWarnException();
        if (alarm.warnExceptionExtend()) {
            return ExceptionUtils.doWarnExceptionExtend(ex, Arrays.asList(doExtendWarnExceptionClasses));
        } else {
            List<String> doWarnExceptionList = new ArrayList<>(doExtendWarnExceptionClasses.length);
            for (Class<? extends Throwable> exceptionClass : doExtendWarnExceptionClasses) {
                doWarnExceptionList.add(exceptionClass.getName());
            }
            return ExceptionUtils.doWarnExceptionName(ex, doWarnExceptionList);
        }
    }
}
