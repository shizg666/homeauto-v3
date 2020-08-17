package com.landleaf.homeauto.contact.screen.handle.mqtt.from.response;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttDeviceStatusReadResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.FamilySceneSetReplyPayload;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 云端控制场景大屏响应处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilySceneSetResponseHandle {

    @Autowired
    private MqttCloudToScreenMessageResponseService mqttCloudToScreenMessageResponseService;

    public void handlerRequest(FamilySceneSetReplyPayload replyPayload) {


        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttDeviceStatusReadResponseDTO readResponseDTO = new ScreenMqttDeviceStatusReadResponseDTO();
        readResponseDTO.setScreenMac(header.getScreenMac());


        readResponseDTO.setMessage(replyPayload.getMessage());
        readResponseDTO.setCode(replyPayload.getCode());

        mqttCloudToScreenMessageResponseService.response(readResponseDTO, header.getMessageId(), ContactScreenNameEnum.FAMILY_SCENE_SET.getCode());

    }

}
