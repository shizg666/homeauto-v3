package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeviceAlarmUploadDTO
 * @Description: 设备报警上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttDeviceAlarmUploadDTO extends ScreenMqttUploadBaseDTO {

    /**
     * 报警设备设备号
     */
    private Integer deviceSn;
    /**
     * 详细信息
     */
    List<ScreenMqttAlarmMsgItemDTO> data;

}
