package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttSceneStatusUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.FamilySceneStatusChangeRequestPayload;
import com.landleaf.homeauto.contact.screen.handle.AbstractRequestHandler;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大屏上报场景状态变化处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilySceneStatusUpdateHandle extends AbstractRequestHandler {

    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;

    public void handlerRequest(FamilySceneStatusChangeRequestPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttSceneStatusUploadDTO uploadDTO = new ScreenMqttSceneStatusUploadDTO();
        uploadDTO.setFamilyCode(header.getFamilyCode());
        uploadDTO.setScreenMac(header.getScreenMac());

        String outerMessageId = header.getMessageId();

        uploadDTO.setCurrentSceneId(requestPayload.getCurrentSceneId());

        mqttScreenToCloudMessageReportService.upload(new ScreenMqttSceneStatusUploadDTO(), ContactScreenNameEnum.FAMILY_SCENE_STATUS_UPDATE.getCode(), outerMessageId);

    }

}
