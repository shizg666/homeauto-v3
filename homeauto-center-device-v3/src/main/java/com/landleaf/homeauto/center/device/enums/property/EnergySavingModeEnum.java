package com.landleaf.homeauto.center.device.enums.property;

/**
 * 节能模式属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum EnergySavingModeEnum {

    /**
     * 居家
     */
    IN_HOME("in_home"),

    /**
     * 离家
     */
    OUT_HOME("out_home"),

    /**
     * 默认值
     */
    DEFAULT(IN_HOME.code);

    private String code;

    EnergySavingModeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
