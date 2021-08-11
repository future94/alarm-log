package com.future94.alarm.log.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weilai
 */
public class ThrowableUtils {

    public static String workWeixinContent(Throwable throwable) {
        return defaultContent(throwable);
    }

    public static String dingtalkContent(Throwable throwable) {
        return defaultContent(throwable);
    }

    public static Map<String, String> mailSubjectContent(Throwable throwable) {
        Map<String, String> result = new HashMap<>();
        StackTraceElement[] trace = throwable.getStackTrace();
        StringBuilder content = new StringBuilder();
        content.append(throwable.toString());
        for (StackTraceElement traceElement : trace) {
            content.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;at ").append(traceElement);
        }
        result.put("subject", throwable.toString());
        result.put("content", content.toString());
        return result;
    }

    private static String defaultContent(Throwable throwable) {
        StackTraceElement[] trace = throwable.getStackTrace();
        StringBuilder content = new StringBuilder();
        content.append(throwable.toString());
        for (StackTraceElement traceElement : trace) {
            content.append("\n    at ").append(traceElement);
        }
        return content.toString();
    }
}
