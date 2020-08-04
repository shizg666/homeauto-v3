package com.landleaf.homeauto.common.domain.dto.screen.payload.notice;

import lombok.Data;

/**
 * 大屏上报设备状态payload
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceStatusUpdateReplyPayload {


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
