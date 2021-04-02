package com.landleaf.homeauto.center.device.enums.property;

/**
 * 布防状态属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum ArmingStateEnum {

    /**
     * 布防
     */
    ARM("arm"),

    /**
     * 撤防
     */
    DISARM("disarm"),

    /**
     * 默认值
     */
    DEFAULT(DISARM.code);

    private String code;

    ArmingStateEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
