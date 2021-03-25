package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 读取设备状态DTO
 */
@Data
public class AdapterDeviceStatusReadDTO extends AdapterMessageBaseDTO {

    /**
     * 通信编码
     */
    private List<String> items;
}
