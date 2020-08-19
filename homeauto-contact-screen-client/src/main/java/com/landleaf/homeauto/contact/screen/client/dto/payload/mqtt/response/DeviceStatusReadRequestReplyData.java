package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 读取设备状态响应数据
 *
 * @author wenyilu
 */
@Data
public class DeviceStatusReadRequestReplyData {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 具体返回值
     */
    private List<ContactScreenDeviceAttribute> items;


}
