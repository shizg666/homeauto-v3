package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备协议等信息
 **/
@Data
public class ScreenFamilyDeviceInfoProtocolDTO {

    /**
     * 协议类型：协议多个以，分隔
     */
    private String protocol;
    /**
     * 设备地址
     */
    private String addressCode;





}
