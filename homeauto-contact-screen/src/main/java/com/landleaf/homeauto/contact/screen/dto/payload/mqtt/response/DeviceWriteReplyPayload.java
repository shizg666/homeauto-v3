package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response;

import lombok.Data;

/**
 * 控制设备写入返回数据
 * @author wenyilu
 */
@Data
public class DeviceWriteReplyPayload {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;



}
