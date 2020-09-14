package com.landleaf.homeauto.center.websocket.constant;

/**
 * 推送消息枚举
 *
 * @author Yujiumin
 * @version 2020/9/14
 */
public enum MessageEnum {

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
    FAMILY_AUTH(3, "家庭授权消息");

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
