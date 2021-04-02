package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenHVACAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttHVACFaultUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenHVACAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.HVACFaultUploadRequestPayload;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏上报暖通故障处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class HVACFaultUploadHandle {


    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;
    /**
     * 大屏上报暖通故障
     * 该请求目前只用于3.0
     * @param requestPayload
     */
    public void handlerRequest(HVACFaultUploadRequestPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttHVACFaultUploadDTO uploadDTO = new ScreenMqttHVACFaultUploadDTO();
        uploadDTO.setScreenMac(header.getScreenMac());

        String outerMessageId = header.getMessageId();

        List<ContactScreenHVACAttribute> attributes = requestPayload.getData().getItems();

        List<ScreenHVACAttributeDTO> data = attributes.stream().map(i -> {
            ScreenHVACAttributeDTO attributeDTO = new ScreenHVACAttributeDTO();
            BeanUtils.copyProperties(i, attributeDTO);
            attributeDTO.setAttrType(i.getAttrType());
            attributeDTO.setAttrValue(i.getAttrValue());
            attributeDTO.setLocation(i.getLocation());
            return attributeDTO;
        }).collect(Collectors.toList());

        uploadDTO.setItems(data);

        mqttScreenToCloudMessageReportService.upload(uploadDTO, ContactScreenNameEnum.HVAC_FAULT_UPLOAD.getCode(),outerMessageId);
    }

}
