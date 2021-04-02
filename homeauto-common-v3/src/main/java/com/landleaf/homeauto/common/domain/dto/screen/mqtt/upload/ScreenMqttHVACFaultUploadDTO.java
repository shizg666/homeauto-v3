package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenHVACAttributeDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenMqttHVACFaultUploadDTO
 * @Description: 设备故障上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttHVACFaultUploadDTO extends ScreenMqttUploadBaseDTO {


    /**
     * 具体返回值
     */
    private List<ScreenHVACAttributeDTO> items;
}
