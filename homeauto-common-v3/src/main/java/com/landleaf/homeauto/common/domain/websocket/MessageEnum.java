package com.landleaf.homeauto.common.domain.websocket;

/**
 * 消息类型枚举
 *
 * @author Yujiumin
 * @version 2020/9/14
 */
public enum MessageEnum {

    /**
     * 心跳消息
     */
    HEARTBEAT(0, "心跳消息"),

    /**
     * 设备状态消息
     */
    DEVICE_STATUS(1, "设备状态消息"),

    /**
     * 安防报警消息
     */
    SECURITY_ALARM(2, "安防报警消息"),

    /**
     * 家庭授权消息
     */
    FAMILY_AUTH(3, "家庭授权消息"),

    /**
     * 家庭设备按品类运行状态统计
     */
    DEVICE_CATEGORY_TOTAL(4, "家庭设备按品类运行状态统计");

    Integer code;

    String title;

    MessageEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer code() {
        return this.code;
    }
}
