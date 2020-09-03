package com.landleaf.homeauto.contact.screen.service;

/**
 * @ClassName MqttClientOnlineCheckService
 * @Description: mqtt客户端是否在线校验
 * @Author wyl
 * @Date 2020/9/3
 * @Version V1.0
 **/
public interface MqttClientOnlineCheckService {


    /**
     * 校验客户端是否在线
     * @param screenMac  clientId
     * @return
     */
    Boolean checkClientOnline(String screenMac);
}
