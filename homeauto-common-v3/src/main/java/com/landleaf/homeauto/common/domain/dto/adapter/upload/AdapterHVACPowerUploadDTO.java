package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.ScreenPowerAttributeDTO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName AdapterHVACPowerUploadDTO
 * @Description: 功率上报DTO
 **/
@Data
public class AdapterHVACPowerUploadDTO extends AdapterMessageUploadDTO {


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
