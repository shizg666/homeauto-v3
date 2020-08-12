package com.landleaf.homeauto.contact.screen.handle.mqtt.from.response;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttDeviceStatusReadResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.DeviceStatusReadRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.handle.AbstractRequestHandler;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 云端读取设备状态大屏响应处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class DeviceStatusReadResponseHandle extends AbstractRequestHandler {

    @Autowired
    private MqttCloudToScreenMessageResponseService mqttCloudToScreenMessageResponseService;

    /**
     * 读取状态数据响应
     *
     * @param replyPayload
     */
    public void handlerRequest(DeviceStatusReadRequestReplyPayload replyPayload) {

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttDeviceStatusReadResponseDTO readResponseDTO = new ScreenMqttDeviceStatusReadResponseDTO();
        readResponseDTO.setFamilyCode(header.getFamilyCode());
        readResponseDTO.setScreenMac(header.getScreenMac());

        List<ContactScreenDeviceAttribute> attributes = replyPayload.getData();

        List<ScreenDeviceAttributeDTO> data = attributes.stream().map(i -> {
            ScreenDeviceAttributeDTO screenDeviceAttributeDTO = new ScreenDeviceAttributeDTO();
            BeanUtils.copyProperties(i, screenDeviceAttributeDTO);
            return screenDeviceAttributeDTO;
        }).collect(Collectors.toList());

        readResponseDTO.setData(data);
        readResponseDTO.setMessage(replyPayload.getMessage());
        readResponseDTO.setCode(replyPayload.getCode());
        mqttCloudToScreenMessageResponseService.response(readResponseDTO, header.getMessageId(),ContactScreenNameEnum.DEVICE_STATUS_READ.getCode());

    }

}
