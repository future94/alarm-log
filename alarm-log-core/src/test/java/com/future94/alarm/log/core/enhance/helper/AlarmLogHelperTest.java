package com.future94.alarm.log.core.enhance.helper;

import org.junit.jupiter.api.Test;
import org.slf4j.MarkerFactory;

/**
 * @author weilai
 */
class AlarmLogHelperTest {

    @Test
    void getPrintLogInstance() {
        AlarmLogHelper.getPrintLogInstance().error("123");
        AlarmLogHelper.getPrintLogInstance().error("123", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error("123:{}", 456);
        AlarmLogHelper.getPrintLogInstance().error("123:{}", 456, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error("123:{}:{}", 456, 789);
        AlarmLogHelper.getPrintLogInstance().error("123:{}:{}", 456, 789, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error("123:{}:{}:{}", 456, 789, "111");
        AlarmLogHelper.getPrintLogInstance().error("123:{}:{}:{}", 456, 789, "111", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123");
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}", 456);
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}", 456, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}:{}", 456, 789);
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}:{}", 456, 789, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}:{}:{}", 456, 789, "111");
        AlarmLogHelper.getPrintLogInstance().error(MarkerFactory.getMarker("test"), "123:{}:{}:{}", 456, 789, "111", new RuntimeException());
    }

    @Test
    void testGetPrintLogInstance() {
        AlarmLogHelper.getPrintLogInstance(true).error("123");
        AlarmLogHelper.getPrintLogInstance(true).error("123", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}", 456);
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}", 456, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}:{}", 456, 789);
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}:{}", 456, 789, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}:{}:{}", 456, 789, "111");
        AlarmLogHelper.getPrintLogInstance(true).error("123:{}:{}:{}", 456, 789, "111", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123");
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123", new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}", 456);
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}", 456, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}:{}", 456, 789);
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}:{}", 456, 789, new RuntimeException());
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}:{}:{}", 456, 789, "111");
        AlarmLogHelper.getPrintLogInstance(true).error(MarkerFactory.getMarker("test"), "123:{}:{}:{}", 456, 789, "111", new RuntimeException());
    }
}