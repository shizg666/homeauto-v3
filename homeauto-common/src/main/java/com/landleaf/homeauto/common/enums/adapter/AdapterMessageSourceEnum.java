package com.landleaf.homeauto.common.enums.adapter;


/**
 * 通讯消息来源
 *
 * @author wenyilu
 */
public enum AdapterMessageSourceEnum {
    /**
     * APP请求
     */
    APP_REQUEST("app_request", "APP请求"),
    /**
     * 系统重试请求
     */
    SYSTEM_RETRY_REQUEST("system_retry_request", "系统重试请求"),

    ;

    private String name;
    private String des;

    AdapterMessageSourceEnum(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
