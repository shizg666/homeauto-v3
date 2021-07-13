package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.ScreenPowerAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttHVACPowerUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.ContactScreenPowerAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.HVACPowerUploadRequestPayload;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏上报功率处理类
 *
 */
@Component("hVACPowerUploadHandle")
@Slf4j
public class HVACPowerUploadHandle {


    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;
    /**
     * 大屏上报功率故障
     *
     * @param requestPayload
     */
    public void handlerRequest(HVACPowerUploadRequestPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        log.info("进入功率header：",header.toString());

        ScreenMqttHVACPowerUploadDTO uploadDTO = new ScreenMqttHVACPowerUploadDTO();
        uploadDTO.setScreenMac(header.getScreenMac());

        uploadDTO.setDeviceSn(requestPayload.getData().getDeviceSn());

        uploadDTO.setProductCode(requestPayload.getData().getProductCode());

        String outerMessageId = header.getMessageId();

        List<ContactScreenPowerAttribute> attributes = requestPayload.getData().getItems();

        List<ScreenPowerAttributeDTO> data = attributes.stream().map(i -> {
            ScreenPowerAttributeDTO attributeDTO = new ScreenPowerAttributeDTO();
            BeanUtils.copyProperties(i, attributeDTO);
            attributeDTO.setAttrValue(i.getAttrValue());
            attributeDTO.setAttrTag(i.getAttrTag());
            attributeDTO.setPowerTime(i.getPowerTime());
            log.info("----attributeDTO:{}",attributeDTO.toString());

            return attributeDTO;
        }).collect(Collectors.toList());

        uploadDTO.setItems(data);
        log.info("----upload:{}",uploadDTO.toString());

        mqttScreenToCloudMessageReportService.upload(uploadDTO, ContactScreenNameEnum.HVAC_POWER_UPLOAD.getCode(),outerMessageId);
    }

}
