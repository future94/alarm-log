package com.future94.alarm.log.core.enhance.helper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weilai
 */
@Slf4j
public class AlarmLogHelper {

    public static PrintLogHelper getPrintLogInstance(boolean alarm) {
        return new PrintLogHelper(alarm);
    }

    public static PrintLogHelper getPrintLogInstance() {
        return new PrintLogHelper(false);
    }
}
