package com.future94.alarm.log.common.context;

import com.future94.alarm.log.common.dto.AlarmLogSimpleConfig;
import com.future94.alarm.log.common.utils.ExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author weilai
 */
public class AlarmLogContext {

    private static final Logger logger = LoggerFactory.getLogger(AlarmLogContext.class);

    @Getter
    @Setter
    private static int maxRetryTimes = 3;

    @Getter
    @Setter
    private static int retrySleepMillis = 1000;

    @Getter
    @Setter
    private static Boolean printStackTrace = false;

    @Getter
    @Setter
    private static Boolean simpleWarnInfo = false;

    private static Boolean warnExceptionExtend = false;

    private static List<String> doWarnExceptionList = new ArrayList<>();

    private static List<Class<? extends Throwable>> doExtendWarnExceptionList = new ArrayList<>();

    @Getter
    @Setter
    private static AlarmMessageContext alarmMessageContext = new DefaultAlarmMessageContext();

    private static AlarmLogSimpleConfig simpleConfig;

    public static AlarmLogSimpleConfig getSimpleConfig () {
        if (Objects.isNull(simpleConfig)) {
            simpleConfig = AlarmLogSimpleConfig.builder().printStackTrace(printStackTrace).simpleWarnInfo(simpleWarnInfo).build();
        }
        return simpleConfig;
    }

    public static Boolean getWarnExceptionExtend() {
        return warnExceptionExtend;
    }

    public static void setWarnExceptionExtend(Boolean warnExceptionExtend) {
        AlarmLogContext.warnExceptionExtend = warnExceptionExtend;
        if (warnExceptionExtend && !AlarmLogContext.doWarnExceptionList.isEmpty()) {
            genExtendWarnExceptionList();
        }
    }

    public static List<String> getDoWarnExceptionList() {
        return doWarnExceptionList;
    }

    public static void addDoWarnExceptionList(List<String> doWarnExceptionList) {
        AlarmLogContext.doWarnExceptionList.addAll(doWarnExceptionList);
        if (AlarmLogContext.warnExceptionExtend) {
            genExtendWarnExceptionList(doWarnExceptionList);
        }
    }

    public static void setDoWarnExceptionList(List<String> doWarnExceptionList) {
        AlarmLogContext.doWarnExceptionList = doWarnExceptionList;
        if (AlarmLogContext.warnExceptionExtend) {
            genExtendWarnExceptionList();
        }
    }

    public static boolean doWarnException(Throwable warnExceptionClass) {
        return AlarmLogContext.warnExceptionExtend ? ExceptionUtils.doWarnExceptionExtend(warnExceptionClass, AlarmLogContext.doExtendWarnExceptionList) : ExceptionUtils.doWarnExceptionName(warnExceptionClass, AlarmLogContext.doWarnExceptionList);
    }

    @SuppressWarnings("unchecked")
    private static void genExtendWarnExceptionList() {
        for (String className : AlarmLogContext.doWarnExceptionList) {
            try {
                AlarmLogContext.doExtendWarnExceptionList.add((Class<? extends Throwable>) Class.forName(className));
            } catch (ClassNotFoundException e) {
                logger.error("init AlarmLogContext classNotFoundException, className [{}]", className);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void genExtendWarnExceptionList(List<String> doWarnExceptionList) {
        for (String className : doWarnExceptionList) {
            try {
                AlarmLogContext.doExtendWarnExceptionList.add((Class<? extends Throwable>) Class.forName(className));
            } catch (ClassNotFoundException e) {
                logger.error("init AlarmLogContext classNotFoundException, className [{}]", className);
            }
        }
    }
}
