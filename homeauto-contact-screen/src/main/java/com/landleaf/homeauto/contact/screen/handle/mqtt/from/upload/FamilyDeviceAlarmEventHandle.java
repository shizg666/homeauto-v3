package com.landleaf.homeauto.contact.screen.handle.mqtt.from.upload;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttAlarmMsgItemDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttDeviceAlarmUploadDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.payload.ContaceScreenAlarmMsgItem;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.FamilyEventAlarmPayload;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏上报安防报警事件处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyDeviceAlarmEventHandle  {

    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;

    public void handlerRequest(FamilyEventAlarmPayload requestPayload) {
        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenMqttDeviceAlarmUploadDTO uploadDTO = new ScreenMqttDeviceAlarmUploadDTO();
        uploadDTO.setScreenMac(header.getScreenMac());

        String outerMessageId = header.getMessageId();

        List<ContaceScreenAlarmMsgItem> items = requestPayload.getData();

        List<ScreenMqttAlarmMsgItemDTO> data = items.stream().map(i -> {
            ScreenMqttAlarmMsgItemDTO alarmMsgItemDTO = new ScreenMqttAlarmMsgItemDTO();
            BeanUtils.copyProperties(i, alarmMsgItemDTO);
            return alarmMsgItemDTO;
        }).collect(Collectors.toList());

        uploadDTO.setData(data);

        mqttScreenToCloudMessageReportService.upload(new ScreenMqttDeviceAlarmUploadDTO(), ContactScreenNameEnum.FAMILY_DEVICE_ALARM_EVENT.getCode(), outerMessageId);


    }

}
