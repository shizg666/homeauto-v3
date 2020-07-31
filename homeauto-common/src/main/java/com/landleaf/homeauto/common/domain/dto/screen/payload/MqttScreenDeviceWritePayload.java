package com.landleaf.homeauto.common.domain.dto.screen.payload;

import lombok.Data;

import java.util.List;

/**
 * 接收请求payload
 */
@Data
public class MqttScreenDeviceWritePayload {

    /**
     * 写入数据集合
     */
    private List<MqttScreenCommon> items;

    /**
     * 设备号
     */
    private String deviceSn;


}
