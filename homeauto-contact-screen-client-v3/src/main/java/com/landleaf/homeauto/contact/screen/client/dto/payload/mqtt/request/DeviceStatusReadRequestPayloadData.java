package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 读取设备状态payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusReadRequestPayloadData {

    /**
     * 设备号
     */
    private Integer deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


}
