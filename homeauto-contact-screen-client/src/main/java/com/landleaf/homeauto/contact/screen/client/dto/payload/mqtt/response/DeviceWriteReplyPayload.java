package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import lombok.Data;

/**
 * 控制设备写入返回数据
 * @author wenyilu
 */
@Data
public class DeviceWriteReplyPayload {


    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;



}
