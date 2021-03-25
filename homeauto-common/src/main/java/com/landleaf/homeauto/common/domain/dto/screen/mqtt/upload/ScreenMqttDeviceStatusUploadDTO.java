package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeviceStatusUploadDTO
 * @Description: 设备状态上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttDeviceStatusUploadDTO extends ScreenMqttUploadBaseDTO {


    /**
     * 具体返回值
     */
    private List<ScreenDeviceAttributeDTO> items;
}
