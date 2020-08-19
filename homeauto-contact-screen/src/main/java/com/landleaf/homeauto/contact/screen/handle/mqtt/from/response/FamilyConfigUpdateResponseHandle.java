package com.landleaf.homeauto.contact.screen.handle.mqtt.from.response;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttConfigUpdateResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.FamilyConfigUpdateReplyPayload;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 云端配置更新通知大屏响应处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyConfigUpdateResponseHandle {

    @Autowired
    private MqttCloudToScreenMessageResponseService mqttCloudToScreenMessageResponseService;

    /**
     * 大屏反馈配置更新通知结果
     *
     * @param replyPayload
     */
    public void handlerRequest(FamilyConfigUpdateReplyPayload replyPayload) {


        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttConfigUpdateResponseDTO configUpdateResponseDTO = new ScreenMqttConfigUpdateResponseDTO();
        configUpdateResponseDTO.setScreenMac(header.getScreenMac());


        configUpdateResponseDTO.setMessage(replyPayload.getMessage());
        configUpdateResponseDTO.setCode(replyPayload.getCode());

        mqttCloudToScreenMessageResponseService.response(configUpdateResponseDTO, header.getMessageId(), ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode());
    }

}
