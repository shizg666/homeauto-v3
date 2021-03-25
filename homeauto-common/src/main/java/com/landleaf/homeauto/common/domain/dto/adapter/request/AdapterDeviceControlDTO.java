package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import lombok.Data;

import java.util.List;

/**
 * 设备控制DTO
 * @author wenyilu
 */
@Data
public class AdapterDeviceControlDTO extends AdapterMessageBaseDTO {


    /**
     * 写入数据集合
     */
    private List<ScreenDeviceAttributeDTO> data;

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


}
