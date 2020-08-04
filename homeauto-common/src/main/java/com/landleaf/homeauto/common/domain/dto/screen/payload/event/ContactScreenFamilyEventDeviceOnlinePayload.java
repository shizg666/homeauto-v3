package com.landleaf.homeauto.common.domain.dto.screen.payload.event;

import lombok.Data;

/**
 * 大屏报警事件上传
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyEventDeviceOnlinePayload {

    /**
     * 上下线 online offline
     */
    private String status;

    /**
     * 设备号
     */
    private String deviceSn;


}
