package com.future94.alarm.log.core.enhance.log4j2;

import com.future94.alarm.log.common.context.AlarmLogContext;
import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.utils.ExceptionUtils;
import com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.PerformanceSensitive;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author weilai
 */
@Plugin(name = "AlarmLog", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, deferChildren = true)
@PerformanceSensitive("allocation")
public class AlarmLog4j2AsyncAppender extends AbstractAppender {

    protected AlarmLog4j2AsyncAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent logEvent) {
        Throwable throwable = logEvent.getThrown();
        if (Objects.nonNull(throwable)
                && (AlarmLogContext.doWarnException(throwable) || ExceptionUtils.doWarnExceptionInstance(throwable))) {
            StackTraceElement stackTraceElement = throwable.getStackTrace()[0];
            AlarmLogWarnServiceFactory.getServiceList().forEach(alarmLogWarnService -> alarmLogWarnService.send(
                    AlarmInfoContext.builder()
                            .message(logEvent.getMessage().getFormattedMessage())
                            .throwableName(throwable.getClass().getName())
                            .threadName(logEvent.getThreadName())
                            .loggerName(logEvent.getLoggerName())
                            .className(stackTraceElement.getClassName())
                            .fileName(stackTraceElement.getFileName())
                            .methodName(stackTraceElement.getMethodName())
                            .lineNumber(stackTraceElement.getLineNumber()).build()
                    , throwable));
        }
    }

    @PluginFactory
    public static AlarmLog4j2AsyncAppender createAppender(@PluginAttribute("name") String name,
                                                          @PluginElement("Filter") final Filter filter,
                                                          @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                          @PluginAttribute ("ignoreExceptions" ) final boolean ignore,
                                                          @PluginAttribute("warnExceptionExtend") Boolean warnExceptionExtend,
                                                          @PluginAttribute("doWarnException") String doWarnException) {
        if (name == null) {
            name = "AlarmLog";
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        Optional.ofNullable(doWarnException).ifPresent(className -> AlarmLogContext.addDoWarnExceptionList(Arrays.asList(className.split(","))));
        Optional.ofNullable(warnExceptionExtend).ifPresent(AlarmLogContext::setWarnExceptionExtend);
        return new AlarmLog4j2AsyncAppender(name, filter, layout, ignore, Property.EMPTY_ARRAY);
    }
}
