package com.landleaf.homeauto.center.device.enums;

/**
 * 暖通模式属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum HvacModeEnum {

    /**
     * 制热
     */
    HOT("hot"),

    /**
     * 制冷
     */
    COLD("cold"),

    /**
     * 默认值
     */
    DEFAULT(COLD.code);

    private String code;

    HvacModeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
