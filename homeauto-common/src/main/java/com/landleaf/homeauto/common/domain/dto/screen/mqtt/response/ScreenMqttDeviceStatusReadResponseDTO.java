package com.landleaf.homeauto.common.domain.dto.screen.mqtt.response;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
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
public class ScreenMqttDeviceStatusReadResponseDTO extends ScreenMqttResponseBaseDTO {


    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


    /**
     * 具体返回值
     */
    private List<ScreenDeviceAttributeDTO> data;
}
