package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.ContactScreenPowerAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * 大屏上报功率payload
 *
 */
@Data
@Builder
public class HVACPowerRequestData {

    /**
     * 设备号
     */
    private Integer deviceSn;
    /**
     * 产品编码
     */
    private Integer productCode;

    /**
     * 具体返回值
     */
    private List<ContactScreenPowerAttribute> items;

    public HVACPowerRequestData() {
    }

    public HVACPowerRequestData(Integer deviceSn,Integer productCode,List<ContactScreenPowerAttribute> items) {
        this.items = items;
        this.deviceSn = deviceSn;
        this.productCode=productCode;
    }
}
