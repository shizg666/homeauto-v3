package com.landleaf.homeauto.contact.screen.client.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.common.util.RandomUtil;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.DeviceStatusUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.FamilyEventAlarmPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.ScreenSceneSetRequestPayload;
import com.landleaf.homeauto.contact.screen.client.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.client.enums.ContactScreenNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttRequestServiceImpl implements MqttRequestService{


    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;

    @Override
    public void uploadControlScene(String screenMac, ScreenSceneSetRequestPayload payload) {

        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .screenMac(screenMac)
                .messageId(RandomUtil.generateString(8)).name(ContactScreenNameEnum.SCREEN_SCENE_SET_UPLOAD.getCode()).build();


        ContactScreenMqttRequest requestData = ContactScreenMqttRequest.builder().header(header).payload(payload).build();

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_SCREEN_TO_CLOUD.getTopic().concat(screenMac), JSON.toJSONString(requestData), QosEnumConst.QOS_0);

    }

    @Override
    public void uploadDeviceStatus(String screenMac, DeviceStatusUpdateRequestPayload payload) {

        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .screenMac(screenMac)
                .messageId(RandomUtil.generateString(8)).name(ContactScreenNameEnum.DEVICE_STATUS_UPDATE.getCode()).build();


        ContactScreenMqttRequest requestData = ContactScreenMqttRequest.builder().header(header).payload(payload).build();

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_UPLOAD_TO_CLOUD.getTopic().concat(screenMac), JSON.toJSONString(requestData), QosEnumConst.QOS_0);
    }

    @Override
    public void uploadAlarmEvent(String screenMac, FamilyEventAlarmPayload payload) {
        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .screenMac(screenMac)
                .messageId(RandomUtil.generateString(8)).name(ContactScreenNameEnum.FAMILY_SECURITY_ALARM_EVENT.getCode()).build();


        ContactScreenMqttRequest requestData = ContactScreenMqttRequest.builder().header(header).payload(payload).build();

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_SCREEN_TO_CLOUD.getTopic().concat(screenMac), JSON.toJSONString(requestData), QosEnumConst.QOS_0);
    }
}
