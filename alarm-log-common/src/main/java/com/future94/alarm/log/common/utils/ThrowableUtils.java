package com.future94.alarm.log.common.utils;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.AlarmLogContext;
import com.future94.alarm.log.common.dto.AlarmMailContent;

import java.util.Objects;

/**
 * @author weilai
 */
public class ThrowableUtils {

    private static final String SEPARATOR = "\n";
    private static final String HTML_SEPARATOR = "<br />";

    public static String workWeixinContent(AlarmInfoContext context, Throwable throwable) {
        return defaultContent(context, throwable, SEPARATOR);
    }

    public static String dingtalkContent(AlarmInfoContext context, Throwable throwable) {
        return defaultContent(context, throwable, SEPARATOR);
    }

    public static AlarmMailContent mailSubjectContent(AlarmInfoContext context, Throwable throwable) {
        return new AlarmMailContent(context.getMessage(), defaultContent(context, throwable, HTML_SEPARATOR));
    }

    private static String defaultContent(AlarmInfoContext context, Throwable throwable, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getMessage()).append(separator);
        if (!AlarmLogContext.getSimpleWarnInfo()) {
            stringBuilder.append("级别:").append(context.getLevel()).append(separator);
            if (Objects.nonNull(context.getThrowableName())) {
                stringBuilder.append("异常:").append(context.getThrowableName()).append(separator);
            }
            stringBuilder.append("线程:").append(context.getThreadName()).append(separator);
            stringBuilder.append("位置信息:").append(context.getClassName()).append(".").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : context.getFileName() != null && context.getLineNumber() >= 0 ? "(" + context.getFileName() + ":" + context.getLineNumber() + ")" : context.getFileName() != null ? "(" + context.getFileName() + ")" : "(Unknown Source)");
            stringBuilder.append(separator);
        }
        if (AlarmLogContext.getPrintStackTrace()) {
            stringBuilder.append(printTrace(throwable));
        }
        return stringBuilder.toString();
    }

    private static String printTrace(Throwable throwable) {
        if (Objects.isNull(throwable)) {
            return "";
        }
        StackTraceElement[] trace = throwable.getStackTrace();
        StringBuilder content = new StringBuilder();
        content.append(throwable.toString());
        for (StackTraceElement traceElement : trace) {
            content.append("\n    at ").append(traceElement);
        }
        return content.toString();
    }

    private static boolean isNativeMethod(int lineNumber) {
        return lineNumber == -2;
    }

}
