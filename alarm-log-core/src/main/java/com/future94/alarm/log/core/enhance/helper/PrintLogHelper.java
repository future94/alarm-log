package com.future94.alarm.log.core.enhance.helper;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.thread.AlarmLogThreadFactory;
import com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weilai
 */
@Slf4j
public class PrintLogHelper {

    private ExecutorService executorService = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), AlarmLogThreadFactory.create("alarm-log-helper-", false));

    private boolean alarm;

    private static final String LEVEL_TRACE = "TRACE";

    private static final String LEVEL_DEBUG = "DEBUG";

    private static final String LEVEL_INFO = "INFO";

    private static final String LEVEL_WARN = "WARN";

    private static final String LEVEL_ERROR = "ERROR";

    public PrintLogHelper(boolean alarm) {
        this.alarm = alarm;
    }

    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public void trace(String msg){
        alarmLog(LEVEL_TRACE, null, null, msg);
    }

    public void trace(String format, Object arg){
        alarmLog(LEVEL_TRACE,null, null, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2){
        alarmLog(LEVEL_TRACE,null, null, format, arg1, arg2);
    }

    public void trace(String format, Object... arguments){
        alarmLog(LEVEL_TRACE,null, null, format, arguments);
    }

    public void trace(String msg, Throwable t){
        alarmLog(LEVEL_TRACE, t, null, msg);
    }

    public boolean isTraceEnabled(Marker marker){
        return log.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg){
        alarmLog(LEVEL_TRACE,null, marker, msg);
    }

    public void trace(Marker marker, String format, Object arg){
        alarmLog(LEVEL_TRACE,null, marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2){
        alarmLog(LEVEL_TRACE,null, marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... argArray){
        alarmLog(LEVEL_TRACE,null, marker, format, argArray);
    }

    public void trace(Marker marker, String msg, Throwable t){
        alarmLog(LEVEL_TRACE, t, marker, msg);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public void debug(String msg){
        alarmLog(LEVEL_DEBUG, null, null, msg);
    }

    public void debug(String format, Object arg){
        alarmLog(LEVEL_DEBUG, null, null, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2){
        alarmLog(LEVEL_DEBUG, null, null, format, arg1, arg2);
    }

    public void debug(String format, Object... arguments){
        alarmLog(LEVEL_DEBUG, null, null, format, arguments);
    }

    public void debug(String msg, Throwable t){
        alarmLog(LEVEL_DEBUG, t, null, msg);
    }

    public boolean isDebugEnabled(Marker marker){
        return log.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg){
        alarmLog(LEVEL_DEBUG, null, marker, msg);
    }

    public void debug(Marker marker, String format, Object arg){
        alarmLog(LEVEL_DEBUG, null, marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2){
        alarmLog(LEVEL_DEBUG, null, marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... argArray){
        alarmLog(LEVEL_DEBUG, null, marker, format, argArray);
    }

    public void debug(Marker marker, String msg, Throwable t){
        alarmLog(LEVEL_DEBUG, t, marker, msg);
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public void info(String msg) {
        alarmLog(LEVEL_INFO, null, null, msg);
    }

    public void info(String format, Object arg){
        alarmLog(LEVEL_INFO, null, null, format, arg);
    }

    public void info(String format, Object arg1, Object arg2){
        alarmLog(LEVEL_INFO, null, null, format, arg1, arg2);
    }

    public void info(String format, Object... arguments){
        alarmLog(LEVEL_INFO, null, null, format, arguments);
    }

    public void info(String msg, Throwable t){
        alarmLog(LEVEL_INFO, t, null, msg);
    }

    public boolean isInfoEnabled(Marker marker){
        return log.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg){
        alarmLog(LEVEL_INFO, null, marker, msg);
    }

    public void info(Marker marker, String format, Object arg){
        alarmLog(LEVEL_INFO, null, marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2){
        alarmLog(LEVEL_INFO, null, marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... argArray){
        alarmLog(LEVEL_INFO, null, marker, format, argArray);
    }

    public void info(Marker marker, String msg, Throwable t){
        alarmLog(LEVEL_INFO, t, marker, msg);
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public void warn(String msg){
        alarmLog(LEVEL_WARN, null, null, msg);
    }

    public void warn(String format, Object arg){
        alarmLog(LEVEL_WARN, null, null, format, arg);
    }

    public void warn(String format, Object arg1, Object arg2){
        alarmLog(LEVEL_WARN, null, null, format, arg1, arg2);
    }

    public void warn(String format, Object... arguments){
        alarmLog(LEVEL_WARN, null, null, format, arguments);
    }

    public void warn(String msg, Throwable t){
        alarmLog(LEVEL_WARN, t, null, msg);
    }

    public boolean isWarnEnabled(Marker marker){
        return log.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg){
        alarmLog(LEVEL_WARN, null, marker, msg);
    }

    public void warn(Marker marker, String format, Object arg){
        alarmLog(LEVEL_WARN, null, marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2){
        alarmLog(LEVEL_WARN, null, marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... argArray){
        alarmLog(LEVEL_WARN, null, marker, format, argArray);
    }

    public void warn(Marker marker, String msg, Throwable t){
        alarmLog(LEVEL_WARN, t, marker, msg);
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public void error(String msg){
        alarmLog(LEVEL_ERROR, null, null, msg);
    }

    public void error(String format, Object arg){
        alarmLog(LEVEL_ERROR, null, null, format, arg);
    }

    public void error(String format, Object arg1, Object arg2){
        alarmLog(LEVEL_ERROR, null, null, format, arg1, arg2);
    }

    public void error(String format, Object... arguments){
        alarmLog(LEVEL_ERROR, null, null, format, arguments);
    }

    public void error(String msg, Throwable t){
        alarmLog(LEVEL_ERROR, t, null, msg);
    }

    public boolean isErrorEnabled(Marker marker){
        return log.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg){
        alarmLog(LEVEL_ERROR, null, marker, msg);
    }

    public void error(Marker marker, String format, Object arg){
        alarmLog(LEVEL_ERROR, null, marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2){
        alarmLog(LEVEL_ERROR, null, marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... argArray){
        alarmLog(LEVEL_ERROR, null, marker, format, argArray);
    }

    public void error(Marker marker, String msg, Throwable t){
        alarmLog(LEVEL_ERROR, t, marker, msg);
    }

    private void alarmLog(String level, Throwable throwable, Marker marker, String format, Object... args) {
        StackTraceElement callMethodStackTrace = getCallMethodStackTrace();
        if (args.length > 0) {
            Object arg = args[args.length - 1];
            if (arg instanceof Throwable) {
                throwable = (Throwable) arg;
            }
        }
        Throwable finalThrowable = throwable;
        executorService.execute(() -> {
            printLog(callMethodStackTrace.getClassName(), level.toLowerCase(), finalThrowable, marker, format, args);
            if (alarm) {
                sendMessage(callMethodStackTrace, level, finalThrowable, format, args);
            }
        });
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private void printLog(String callerClassName, String methodName, Throwable throwable, Marker marker, Object... parameters) {
        Logger logger = LoggerFactory.getLogger(callerClassName);
        List<Class<?>> parameterTypes = new ArrayList<>();
        Optional.ofNullable(marker).ifPresent(m -> parameterTypes.add(Marker.class));
        Object message = parameters[0];
        parameterTypes.add(message.getClass());
        Object[] parameter = (Object[]) parameters[1];
        for (Object o : parameter) {
            if (o instanceof Throwable) {
                continue;
            }
            parameterTypes.add(Object.class);
        }
        if (Objects.nonNull(throwable)) {
            if ( parameter.length > 0) {
                if (parameter[0] instanceof Throwable) {
                    parameterTypes.add(Throwable.class);
                } else {
                    parameterTypes.add(Object.class);
                }
            } else {
                parameterTypes.add(Throwable.class);
            }
        }
        Class<?>[] classes;
        if (Objects.isNull(marker)) {
            if (parameterTypes.size() > 3) {
                classes = new Class[]{String.class, Object[].class};
            } else {
                classes = new Class[parameterTypes.size()];
                parameterTypes.toArray(classes);
            }
        } else {
            if (parameterTypes.size() > 4) {
                classes = new Class[]{Marker.class, String.class, Object[].class};
            } else {
                classes = new Class[parameterTypes.size()];
                parameterTypes.toArray(classes);
            }
        }
        try {
            Class<? extends Logger> loggerClass = logger.getClass();
            Method method = loggerClass.getMethod(methodName, classes);
            if (Objects.isNull(marker)) {
                if (parameter.length == 0) {
                    if (Objects.isNull(throwable)) {
                        method.invoke(logger, message);
                    } else {
                        method.invoke(logger, message, throwable);
                    }
                } else if (parameterTypes.size() > 3) {
                    method.invoke(logger, parameters);
                } else {
                    Object[] p = new Object[parameter.length + 1];
                    p[0] = message;
                    System.arraycopy(parameter, 0, p, 1, parameter.length);
                    method.invoke(logger, p);
                }
            } else {
                if (parameter.length == 0) {
                    if (Objects.isNull(throwable)) {
                        method.invoke(logger, marker, message);
                    } else {
                        method.invoke(logger, marker, message, throwable);
                    }
                } else if (parameterTypes.size() > 4) {
                    method.invoke(logger, marker, message, parameter);
                } else {
                    Object[] p = new Object[parameter.length + 2];
                    p[0] = marker;
                    p[1] = message;
                    System.arraycopy(parameter, 0, p, 2, parameter.length);
                    method.invoke(logger, p);
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw e;
        }
    }

    private void sendMessage(StackTraceElement stackTraceElement, String level, Throwable throwable, String format, Object... args) {
        AlarmLogWarnServiceFactory.getServiceList().forEach(alarmLogWarnService -> alarmLogWarnService.send(
                AlarmInfoContext.builder()
                        .message(String.format(format.replaceAll("\\{}", "%s"), args))
                        .throwableName(Objects.nonNull(throwable) ? throwable.getClass().getName() : null)
                        .level(level)
                        .threadName(Thread.currentThread().getName())
                        .loggerName(stackTraceElement.getClassName())
                        .className(stackTraceElement.getClassName())
                        .fileName(stackTraceElement.getFileName())
                        .lineNumber(stackTraceElement.getLineNumber())
                        .methodName(stackTraceElement.getMethodName()).build()
                , throwable));
    }

    private static StackTraceElement getCallMethodStackTrace() {
        StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
        return stackTraceElements[3];
    }

    /**
     * get caller class name
     *
     * @return {@link StackTraceElement#getClassName()}
     */
    private static String getCallerClassName() {
        return getCallMethodStackTrace().getClassName();
    }

    /**
     * get caller method name
     *
     * @return {@link StackTraceElement#getMethodName()}
     */
    private static String getCallerMethodName() {
        return getCallMethodStackTrace().getMethodName();
    }

    /**
     * get caller code line number
     *
     * @return {@link StackTraceElement#getLineNumber()}
     */
    private static int getCallerCodeLineNumber() {
        return getCallMethodStackTrace().getLineNumber();
    }
}
