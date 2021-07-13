package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.ScreenPowerAttributeDTO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName ScreenMqttHVACPowerUploadDTO
 * @Description: 设备功率上报DTO
 **/
@Data
public class ScreenMqttHVACPowerUploadDTO extends ScreenMqttUploadBaseDTO {


    /**
     * 具体返回值
     */
    private List<ScreenPowerAttributeDTO> items;

    /**
     * 设备号
     */
    private Integer deviceSn;
    /**
     * 产品编码
     */
    private String productCode;
}
