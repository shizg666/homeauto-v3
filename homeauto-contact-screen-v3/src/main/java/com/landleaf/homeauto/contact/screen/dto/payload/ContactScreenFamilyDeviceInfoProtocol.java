package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 设备协议信息
 **/
@Data
public class ContactScreenFamilyDeviceInfoProtocol {

    /**
     * 协议类型：协议多个以，分隔
     */
    private String protocol;
    /**
     * 设备地址
     */
    private String addressCode;

}
