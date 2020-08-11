package com.landleaf.homeauto.common.enums.oauth;


/**
 * app用户类型
 */
public enum AppTypeEnum {
    SMART("smart", "智能家居APP"),
    NO_SMART("non-smart", "自由方舟APP"),
    ;

    private String code;
    private String name;

    AppTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
