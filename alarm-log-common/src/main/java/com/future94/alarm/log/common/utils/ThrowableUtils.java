package com.future94.alarm.log.common.utils;

import com.future94.alarm.log.common.dto.AlarmInfoContext;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> mailSubjectContent(AlarmInfoContext context, Throwable throwable) {
        Map<String, String> result = new HashMap<>(2);
        result.put("subject", context.getMessage());
        result.put("content", defaultContent(context, throwable, HTML_SEPARATOR));
        return result;
    }

    private static String defaultContent(AlarmInfoContext context, Throwable throwable, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getMessage()).append(separator);
        stringBuilder.append("异常:").append(context.getThrowableName()).append(separator);
        stringBuilder.append("线程:").append(context.getThreadName()).append(separator);
        stringBuilder.append("位置信息:").append(context.getClassName()).append(".").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : context.getFileName() != null && context.getLineNumber() >= 0 ? "(" + context.getFileName() + ":" + context.getLineNumber() + ")" : context.getFileName() != null ? "(" + context.getFileName() + ")" : "(Unknown Source)");
        return stringBuilder.toString();
    }

    private static String printTrace(Throwable throwable) {
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
