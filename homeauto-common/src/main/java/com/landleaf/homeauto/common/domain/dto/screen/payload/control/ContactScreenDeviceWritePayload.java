package com.landleaf.homeauto.common.domain.dto.screen.payload.control;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 接收请求payload
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceWritePayload {

    /**
     * 写入数据集合
     */
    private List<ContactScreenDeviceAttribute> items;

    /**
     * 设备号
     */
    private String deviceSn;


}
