package com.landleaf.homeauto.common.mqtt;

import lombok.Data;

/**
 * @Classname MqttClientInfo
 * @Description mqtt客户端信息维护
 * @Date 2021/2/23 16:47
 * @Created by binfoo
 */
@Data
public class MqttClientInfo {
    private String clientid;//客户端标识符
    private String proto_name;//客户端协议名称
    private String ip_address;//客户端的 IP 地址
    private String port;//客户端的端口
    private boolean connected;//客户端是否处于连接状态
    private String connected_at;//客户端连接时间，格式为 "YYYY-MM-DD HH:mm:ss"
    private String disconnected_at;//客户端离线时间，格式为 "YYYY-MM-DD HH:mm:ss"，此字段仅在 connected 为 false 时有效并被返回
    private String created_at;//会话创建时间，格式为 "YYYY-MM-DD HH:mm:ss"

}
