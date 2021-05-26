package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大屏上报设备状态payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusUpdateRequestData {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;

    /**
     * 具体返回值
     */
    private List<ContactScreenDeviceAttribute> items;

    public DeviceStatusUpdateRequestData() {
    }

    public DeviceStatusUpdateRequestData(String deviceSn, String productCode, List<ContactScreenDeviceAttribute> items) {
        this.deviceSn = deviceSn;
        this.productCode = productCode;
        this.items = items;
    }
}
