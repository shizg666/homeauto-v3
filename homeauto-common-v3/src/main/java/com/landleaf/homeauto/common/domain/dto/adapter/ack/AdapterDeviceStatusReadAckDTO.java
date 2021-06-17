package com.landleaf.homeauto.common.domain.dto.adapter.ack;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import lombok.Data;

import java.util.List;

/**
 * 设备状态读取ack
 *
 * @author wenyilu
 */
@Data
public class AdapterDeviceStatusReadAckDTO extends AdapterMessageAckDTO {


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

    /**
     * 具体返回值
     */
    private List<ScreenDeviceAttributeDTO> items;
}
