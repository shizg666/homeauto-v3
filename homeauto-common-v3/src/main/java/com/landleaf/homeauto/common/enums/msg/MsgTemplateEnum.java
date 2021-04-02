package com.landleaf.homeauto.common.enums.msg;

/**
 * @author Lokiy
 * @date 2019/8/29 11:35
 * @description:
 */
public enum MsgTemplateEnum {

    /**
     * 系统消息
     */
    MSG_SYS(0),

    /**
     * 手机消息
     */
    MSG_MOBILE(1),

    /**
     * email消息
     */
    MSG_EMAIL(2),

    /**
     * 推送消息
     */
    MSG_PUSH(3),
    ;

    private Integer type;

    MsgTemplateEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
