package com.landleaf.homeauto.center.device.enums;

/**
 * @Description 1.0
 * @Author zhanghongbin
 * @Date 2020/9/1 17:45
 */
public enum MsgTerminalTypeEnum {
    /**
     * 所有
     */
    All(0),

    /**
     * app
     */
    APP(1),

    /**
     * Screen
     */
    SCREEN(2),

    ;

    private Integer type;
    MsgTerminalTypeEnum(Integer type){
        this.type=type;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }



}
