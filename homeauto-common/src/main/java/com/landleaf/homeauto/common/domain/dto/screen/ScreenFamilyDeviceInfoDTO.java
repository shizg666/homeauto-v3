package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

import java.util.List;

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
     * 产品编号
     */
    private String productCode;

    /**
     * 属性信息
     */
    private List<ScreenDeviceAttributeDTO> attributes;


}
