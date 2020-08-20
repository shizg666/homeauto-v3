package com.landleaf.homeauto.common.enums.device;


/**
 * 家庭终端类型
 * @author wenyilu
 */
public enum TerminalTypeEnum {
    SCREEN(1, "大屏"),
    GATEWAY(2, "网关"),
    ;

    private Integer code;
    private String name;

    TerminalTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
