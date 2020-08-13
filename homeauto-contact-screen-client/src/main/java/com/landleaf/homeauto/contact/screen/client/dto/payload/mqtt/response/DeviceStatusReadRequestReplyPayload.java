package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 读取设备状态响应payload
 * @author wenyilu
 */
@Data
public class DeviceStatusReadRequestReplyPayload {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;

    /**
     * 具体返回值
     */
    private List<ContactScreenDeviceAttribute> data;


}
