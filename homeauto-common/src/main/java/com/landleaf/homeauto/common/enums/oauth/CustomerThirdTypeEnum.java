package com.landleaf.homeauto.common.enums.oauth;


/**
 * app用户三方来源
 * @author pilo
 */
public enum CustomerThirdTypeEnum {
    /**
     * 微信
     */
    WECHAT("wechat", "微信小程序"),
    ;

    private String code;
    private String name;

    CustomerThirdTypeEnum(String code, String name) {
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
