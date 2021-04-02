package com.landleaf.homeauto.center.device.enums.property;

/**
 * 加湿使能属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum HumidificationEnableEnum {

    /**
     * 开
     */
    ON("on"),

    /**
     * 关
     */
    OFF("off"),

    /**
     * 默认值
     */
    DEFAULT(OFF.code);

    private String code;

    HumidificationEnableEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
