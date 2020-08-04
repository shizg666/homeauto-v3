package com.landleaf.homeauto.common.domain.dto.screen.payload.notice;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 大屏上报设备状态payload
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceStatusUpdatePayload {

    /**
     * 数据集合
     */
    private List<ContactScreenDeviceAttribute> items;

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


}
