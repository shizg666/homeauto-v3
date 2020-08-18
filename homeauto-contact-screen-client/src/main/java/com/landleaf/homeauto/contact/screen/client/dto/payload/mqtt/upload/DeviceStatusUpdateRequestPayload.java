package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
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

    public DeviceStatusUpdateRequestPayload() {
    }

    public DeviceStatusUpdateRequestPayload(String deviceSn, String productCode, List<ContactScreenDeviceAttribute> data) {
        this.deviceSn = deviceSn;
        this.productCode = productCode;
        this.data = data;
    }
}
