package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

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
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;


}
