package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import lombok.Data;

/**
 * 读取设备状态DTO
 */
@Data
public class AdapterDeviceStatusReadDTO extends AdapterMessageBaseDTO {

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
