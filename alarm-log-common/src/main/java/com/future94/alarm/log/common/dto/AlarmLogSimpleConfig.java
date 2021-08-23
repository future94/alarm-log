package com.future94.alarm.log.common.dto;

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
public class AlarmLogSimpleConfig {

    private Boolean printStackTrace;

    private Boolean simpleWarnInfo;
}
