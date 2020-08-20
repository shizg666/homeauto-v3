package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import lombok.Data;

/**
 * 安防报警DTO
 *
 * @author wenyilu
 */
@Data
public class AdapterSecurityAlarmMsgItemDTO {

    /**
     * 报警设备设备号
     */
    private String deviceSn;

    /**
     * 大屏存储的报警编码
     */
    private Long alarmId;

    /**
     * 报警类型 1-实时报警，2日志上报
     */
    private Integer alarmType;

    /**
     * 报警设备名称：红外，烟感
     */
    private String alarmDevice;

    /**
     * 防区路数，哪一路防区
     */
    private Integer alarmZone;

    /**
     * 描述 默认 为   "报警"
     */
    private String context;

    /**
     * 报警时间
     */
    private Long alarmTime;

}
