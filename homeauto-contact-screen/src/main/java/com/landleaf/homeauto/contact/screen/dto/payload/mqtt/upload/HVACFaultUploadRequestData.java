package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenHVACAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大屏上报故障payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class HVACFaultUploadRequestData {


    /**
     * 具体返回值
     */
    private List<ContactScreenHVACAttribute> items;

    public HVACFaultUploadRequestData() {
    }

    public HVACFaultUploadRequestData(List<ContactScreenHVACAttribute> items) {
        this.items = items;
    }
}
