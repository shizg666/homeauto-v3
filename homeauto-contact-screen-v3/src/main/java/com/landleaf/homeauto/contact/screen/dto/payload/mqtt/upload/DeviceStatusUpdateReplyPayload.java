package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import lombok.Data;

/**
 * 大屏上报设备状态payload
 * @author wenyilu
 */
@Data
public class DeviceStatusUpdateReplyPayload {



    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
