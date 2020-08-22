package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttScreenSceneSetUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.ScreenSceneSetRequestPayload;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大屏上报控制场景
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ScreenSceneSetUploadHandle {

    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;

    public void handlerRequest(ScreenSceneSetRequestPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttScreenSceneSetUploadDTO uploadDTO = new ScreenMqttScreenSceneSetUploadDTO();
        uploadDTO.setScreenMac(header.getScreenMac());

        String outerMessageId = header.getMessageId();

        uploadDTO.setSceneId(requestPayload.getSceneId());

        mqttScreenToCloudMessageReportService.upload(uploadDTO, ContactScreenNameEnum.SCREEN_SCENE_SET_UPLOAD.getCode(), outerMessageId);

    }

}
