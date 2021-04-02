package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import lombok.Data;

/**
 * 读取设备状态响应payload
 *
 * @author wenyilu
 */
@Data
public class DeviceStatusReadRequestReplyPayload {

    /**
     * 产品编码
     */
    private DeviceStatusReadRequestReplyData data;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;

    public DeviceStatusReadRequestReplyPayload() {
    }

    public DeviceStatusReadRequestReplyPayload(DeviceStatusReadRequestReplyData data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
}
