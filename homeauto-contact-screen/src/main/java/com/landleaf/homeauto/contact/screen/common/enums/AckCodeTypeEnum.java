package com.landleaf.homeauto.contact.screen.common.enums;


/**
 * 是否需要响应枚举
 */
public enum AckCodeTypeEnum {
    NON_REQUIRED("1", "不需要响应"),
    REQUIRED("0", "需要响应");

    public String type;
    public String name;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    AckCodeTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
