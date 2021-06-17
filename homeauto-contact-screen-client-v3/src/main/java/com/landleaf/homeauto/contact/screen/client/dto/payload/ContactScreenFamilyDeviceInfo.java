package com.landleaf.homeauto.contact.screen.client.dto.payload;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 设备信息
 **/
@Data
public class ContactScreenFamilyDeviceInfo {

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 唯一区分标记-设备号
     */
    private Integer deviceSn;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 属性信息
     */
    private List<ContactScreenDeviceAttribute> attributes;


}
