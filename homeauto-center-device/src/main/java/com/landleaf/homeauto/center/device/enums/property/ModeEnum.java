package com.landleaf.homeauto.center.device.enums.property;

/**
 * 模式属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum ModeEnum {

    /**
     * 除湿
     */
    DEHUMIDIFICATION("dehumidification"),

    /**
     * 制热
     */
    HOT("hot"),

    /**
     * 送风
     */
    WIND("wind"),

    /**
     * 制冷
     */
    COLD("cold"),

    /**
     * 默认值
     */
    DEFAULT(DEHUMIDIFICATION.code);

    private String code;

    ModeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
