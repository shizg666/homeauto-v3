package com.landleaf.homeauto.common.enums.screen;


/**
 * mqtt-callback-枚举
 *
 * @author wenyilu
 */
public enum MqttCallBackTypeEnum {

    /**
     * 成功接入
     */
    CLIENT_CONNECT("client_connect", "成功接入"),
    CLIENT_DISCONNECTED("client_disconnected", "连接断开"),
    ;

    public String code;
    public String name;

    MqttCallBackTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
