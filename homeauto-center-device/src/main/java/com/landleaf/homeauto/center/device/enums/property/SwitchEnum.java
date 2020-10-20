package com.landleaf.homeauto.center.device.enums.property;

import java.util.Objects;

/**
 * 开关属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum SwitchEnum {

    /**
     * 开
     */
    ON("on"),

    /**
     * 关
     */
    OFF("off"),

    /**
     * 暂停(暂停不应该是PAUSE吗?怎么会是STOP呢)
     */
    STOP("stop"),

    /**
     * 默认值
     */
    DEFAULT(OFF.code);

    private String code;

    SwitchEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static SwitchEnum getByCode(String code) {
        for (SwitchEnum switchEnum : values()) {
            if (Objects.equals(switchEnum.code, code)) {
                return switchEnum;
            }
        }
        return null;
    }
}
