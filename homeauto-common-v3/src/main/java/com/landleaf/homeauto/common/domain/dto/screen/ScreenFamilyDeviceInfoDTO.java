package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备信息
 **/
@Data
public class ScreenFamilyDeviceInfoDTO {

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 唯一区分标记-设备号
     */
    private String deviceSn;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 485端口号
     */
    private String port;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品协议
     */
    private Integer protocol;


}
