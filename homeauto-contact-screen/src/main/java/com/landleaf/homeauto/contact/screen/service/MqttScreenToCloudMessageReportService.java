package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;

/**
 * 大屏通知消息上报处理器
 *
 * @author wenyilu
 */
public interface MqttScreenToCloudMessageReportService {

    void upload(ScreenMqttUploadBaseDTO screenUploadBaseDTO, String code, String outerMessageId);
}
