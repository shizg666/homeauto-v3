package com.landleaf.homeauto.common.enums.device;


/**
 * 家庭终端类型
 *
 * @author wenyilu
 */
public enum TerminalTypeEnum {

    /**
     * 终端:大屏
     */
    SCREEN(1, "大屏"),

    /**
     * 终端:网关
     */
    GATEWAY(2, "网关");

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

    public static TerminalTypeEnum getTerminal(Integer type) {
        if (type == 1) {
            return SCREEN;
        } else {
            return GATEWAY;
        }
    }
}
