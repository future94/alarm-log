package com.future94.alarm.log.warn.common.factory;

import com.future94.alarm.log.warn.common.AlarmLogWarnService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weilai
 */
public class AlarmLogWarnServiceFactory {

    private static List<AlarmLogWarnService> serviceList = new ArrayList<>();

    public AlarmLogWarnServiceFactory() {
    }

    public AlarmLogWarnServiceFactory(List<AlarmLogWarnService> alarmLogWarnServices) {
        serviceList = alarmLogWarnServices;
    }

    public static void setAlarmLogWarnService(AlarmLogWarnService alarmLogWarnService) {
        serviceList.add(alarmLogWarnService);
    }

    public static List<AlarmLogWarnService> getServiceList () {
        return serviceList;
    }
}
