package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeviceControlDTO
 * @Description: 设备控制DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttDeviceControlDTO extends ScreenMqttBaseDTO {


    /**
     * 写入数据集合
     */
    private List<ScreenDeviceAttributeDTO> data;

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
