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
    private Long deviceId;

    /**
     * 唯一区分标记-设备号
     */
    private Integer deviceSn;

    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;

    /**
     * 关联系统sn号
     */
    private Integer relatedDeviceSn;

    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 品类
     */
    private Integer categoryCode;

    private ScreenFamilyDeviceInfoProtocolDTO deviceProtocol;

    /**
     * 属性列表
     */
    private List<ContactScreenFamilyDeviceAttrInfoDTO> attrs;


}
