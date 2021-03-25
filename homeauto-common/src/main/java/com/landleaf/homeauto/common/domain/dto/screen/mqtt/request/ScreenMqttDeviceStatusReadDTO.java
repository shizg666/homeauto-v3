package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import lombok.Data;

import java.util.List;

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
     * 通信编码
     */
    private List<String > items;
}
