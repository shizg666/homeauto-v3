package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import lombok.Data;

/**
 * @ClassName ContactScreenDeviceStatusReadDTO
 * @Description: 读取设备状态DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttDeviceStatusReadDTO extends ScreenMqttBaseDTO {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;
}
