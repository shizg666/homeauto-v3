package com.landleaf.homeauto.center.device.enums.property;

/**
 * 风量属性枚举(不是很理解为什么风量叫wind speed,而风速叫air volume)
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum WindSpeedEnum {

    /**
     * 低速
     */
    LOW_SPEED("low_speed"),

    /**
     * 中速
     */
    MEDIUM_SPEED("medium_speed"),

    /**
     * 高速
     */
    HIGH_SPEED("high_speed"),

    /**
     * 默认值
     */
    DEFAULT(LOW_SPEED.code);

    private String code;

    WindSpeedEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
