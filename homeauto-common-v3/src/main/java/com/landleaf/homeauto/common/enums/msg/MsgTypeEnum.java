package com.landleaf.homeauto.common.enums.msg;

/**
 * @author Lokiy
 * @date 2019/8/14 16:37
 * @description: 消息类型
 */
public enum MsgTypeEnum {

    /**
     * 公告消息
     */
    NOTICE(1),

    /**
     * 广告消息
     */
    ADVERT(2),

    /**
     * 场景消息
     */
    RCMD_SCENE(3),

    /**
     * 联动消息
     */
    RCMD_LINKAGE(4),
    ;

    private Integer type;

    MsgTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
