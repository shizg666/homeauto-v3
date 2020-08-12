package com.landleaf.homeauto.contact.screen.common.enums;


/**
 * 是否需要响应枚举
 */
public enum AckCodeTypeEnum {
    NON_REQUIRED("1", "不需要响应"),
    REQUIRED("0", "需要响应");

    public String type;
    public String name;

    AckCodeTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
