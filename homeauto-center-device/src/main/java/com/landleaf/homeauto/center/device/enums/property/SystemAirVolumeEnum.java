package com.landleaf.homeauto.center.device.enums.property;

/**
 * 系统风量属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum SystemAirVolumeEnum {

    /**
     * 增强
     */
    ENHANCE("enhance"),

    /**
     * 正常
     */
    NORMAL("normal"),

    /**
     * 默认值
     */
    DEFAULT(NORMAL.code);

    private String code;

    SystemAirVolumeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
