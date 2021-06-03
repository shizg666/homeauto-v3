package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 设备信息
 **/
@Data
public class ContactScreenFamilyDeviceInfo {


    /**
     * 唯一区分标记-设备号
     */
    private String deviceSn;

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
    private String relatedDeviceSn;

    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 品类
     */
    private String categoryCode;

    /**
     * 属性列表
     */
    private List<ContactScreenFamilyDeviceAttrInfo>  attrs;

    private ContactScreenFamilyDeviceInfoProtocol deviceProtocol;

}
