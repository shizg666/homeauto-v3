package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 接收请求payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceWritePayload {

    /**
     * 写入数据集合
     */
    private List<ContactScreenDeviceAttribute> data;

    /**
     * 设备号
     */
    private String deviceSn;

    /**
     * 产品编码
     */
    private String productCode;


}
