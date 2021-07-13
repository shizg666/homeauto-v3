package com.landleaf.homeauto.common.domain.dto.screen.mqtt;

import lombok.Data;

/**
 * @Classname ScreenPowerAttribute
 * @Description TODO
 * @Date 2021/6/15 18:26
 * @Created by binfoo
 */
@Data
public class ScreenPowerAttributeDTO {

    String attrTag;//类似glcPower\glvPower

    /**
     * 属性value值
     */
    String attrValue;
    /**
     * 时间戳
     */
    Long powerTime;

}
