package com.landleaf.homeauto.center.device.enums.property;

/**
 * 风速属性枚举(不是很理解为什么风速叫air volume,而风量叫wind speed)
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum AirVolumeEnum {

    /**
     * 一档
     */
    FIRST_GEAR("first_gear"),

    /**
     * 二档
     */
    SECOND_GEAR("second_gear"),

    /**
     * 三档
     */
    THIRD_GEAR("third_gear"),

    /**
     * 四档
     */
    FOURTH_GEAR("fourth_gear"),

    /**
     * 五档(产品有拼写错误啊)
     */
    FIFTH_GEAR("five_gear"),

    /**
     * 默认
     */
    DEFAULT(FIRST_GEAR.code);

    private String code;

    AirVolumeEnum(String code) {
        this.code = code;
    }
}
