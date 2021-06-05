package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 接收请求payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class DeviceWritePayloadData {

    /**
     * 写入数据集合
     */
    private List<ContactScreenDeviceAttribute> items;

    /**
     * 设备号
     */
    private String deviceSn;

    /**
     * 产品编码
     */
    private String productCode;
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;


}
