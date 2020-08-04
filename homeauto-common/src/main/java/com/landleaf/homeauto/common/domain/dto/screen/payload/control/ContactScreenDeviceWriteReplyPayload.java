package com.landleaf.homeauto.common.domain.dto.screen.payload.control;

import lombok.Data;

import java.util.List;

/**
 * 控制设备写入返回数据
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceWriteReplyPayload {


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
