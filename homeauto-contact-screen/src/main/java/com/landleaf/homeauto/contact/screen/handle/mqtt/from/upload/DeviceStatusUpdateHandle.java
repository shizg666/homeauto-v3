package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttDeviceStatusUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.DeviceStatusUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏上报设备状态变化处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class DeviceStatusUpdateHandle {


    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;
    /**
     * 大屏上报设备状态变
     *
     * @param requestPayload
     */
    public void handlerRequest(DeviceStatusUpdateRequestPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttDeviceStatusUploadDTO uploadDTO = new ScreenMqttDeviceStatusUploadDTO();
        uploadDTO.setScreenMac(header.getScreenMac());

        String outerMessageId = header.getMessageId();

        List<ContactScreenDeviceAttribute> attributes = requestPayload.getData();

        List<ScreenDeviceAttributeDTO> data = attributes.stream().map(i -> {
            ScreenDeviceAttributeDTO screenDeviceAttributeDTO = new ScreenDeviceAttributeDTO();
            BeanUtils.copyProperties(i, screenDeviceAttributeDTO);
            screenDeviceAttributeDTO.setValue(i.getAttrValue());
            screenDeviceAttributeDTO.setCode(i.getAttrTag());
            return screenDeviceAttributeDTO;
        }).collect(Collectors.toList());

        uploadDTO.setItems(data);
        uploadDTO.setDeviceSn(requestPayload.getDeviceSn());
        uploadDTO.setProductCode(requestPayload.getProductCode());

        mqttScreenToCloudMessageReportService.upload(uploadDTO, ContactScreenNameEnum.DEVICE_STATUS_UPDATE.getCode(),outerMessageId);
    }

}
