package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 暖通故障属性
 **/
@Data
public class ScreenHVACAttributeDTO {


    /**
     * 类型值 新风机、空调、PLC
     */
    String attrType;

    /**
     * 属性value值
     */
    String attrValue;

    /**
     * 位置
     */
    String location;


}
