package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大屏上报设备状态payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusUpdateRequestPayload {

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
    private List<ContactScreenDeviceAttribute> data;

}
