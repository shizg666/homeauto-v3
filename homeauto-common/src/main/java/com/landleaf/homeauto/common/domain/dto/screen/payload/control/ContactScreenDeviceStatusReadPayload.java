package com.landleaf.homeauto.common.domain.dto.screen.payload.control;

import lombok.Data;

import java.util.List;

/**
 * 读取设备状态payload
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceStatusReadPayload {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


}
