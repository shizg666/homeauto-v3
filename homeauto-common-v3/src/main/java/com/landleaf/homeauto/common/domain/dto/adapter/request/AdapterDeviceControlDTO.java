package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import io.swagger.models.auth.In;
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
