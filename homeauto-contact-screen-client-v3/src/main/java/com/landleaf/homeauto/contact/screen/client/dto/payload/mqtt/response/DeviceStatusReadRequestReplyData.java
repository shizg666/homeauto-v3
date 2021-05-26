package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 读取设备状态响应数据
 *
 * @author wenyilu
 */
@Data
public class DeviceStatusReadRequestReplyData {

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

    public DeviceStatusReadRequestReplyData() {
    }

    public DeviceStatusReadRequestReplyData(String deviceSn, String productCode, List<ContactScreenDeviceAttribute> items) {
        this.deviceSn = deviceSn;
        this.productCode = productCode;
        this.items = items;
    }
}
