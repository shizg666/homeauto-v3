package com.landleaf.homeauto.common.domain.dto.screen.payload.control;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 读取设备状态payload
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceStatusReadReplyPayload {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;

    /**
     * 具体返回值
     */
    private List<ContactScreenDeviceAttribute> data;


}
