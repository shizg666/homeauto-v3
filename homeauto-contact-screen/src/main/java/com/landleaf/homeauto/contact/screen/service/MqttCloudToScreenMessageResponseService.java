package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;

/**
 * 内部服务向下消息响应处理器
 *
 * @author wenyilu
 */
public interface MqttCloudToScreenMessageResponseService {


    void response(ScreenMqttResponseBaseDTO screenResponseBaseDTO, String outerMessageId, String operateName);

    /**
     * 超时或超次数返回
     * @param screenMac
     * @param innerMessageId
     * @param operateName
     * @param outerMessageId
     */
    void responseErrorMsg(String screenMac, String innerMessageId, String operateName, String outerMessageId);

    /**
     * 返回错误
     * @param screenMac
     * @param innerMessageId
     * @param operateName
     * @param outerMessageId
     * @param errorMsg
     * @param errorCode
     */
    void responseErrorMsg(String screenMac, String innerMessageId, String operateName, String outerMessageId,String errorMsg,Integer errorCode);
}
