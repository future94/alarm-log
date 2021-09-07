package com.future94.alarm.log.common.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author weilai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class AlarmInfoContext {

    private String message;

    private String throwableName;

    private String loggerName;

    private String threadName;

    private String level;
    /**
     Caller's line number.
     */
    private int lineNumber;
    /**
     Caller's file name.
     */
    private String fileName;
    /**
     Caller's fully qualified class name.
     */
    private String className;
    /**
     Caller's method name.
     */
    private String methodName;

}
