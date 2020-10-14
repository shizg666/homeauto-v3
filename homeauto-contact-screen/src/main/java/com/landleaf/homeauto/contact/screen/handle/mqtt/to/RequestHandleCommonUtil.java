package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttSceneControlDTO;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.contact.screen.common.constance.TaskConst;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request.*;
import com.landleaf.homeauto.contact.screen.service.MqttClientOnlineCheckService;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageResponseService;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenTimeoutService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName AbstractRequesthandle
 * @Description: TODO
 * @Author wyl
 * @Date 2020/9/3
 * @Version V1.0
 **/
@Slf4j
@Data
@Component
public class RequestHandleCommonUtil {

    @Autowired
    private MqttCloudToScreenTimeoutService timeoutService;
    @Autowired
    private SyncSendUtil syncSendUtil;
    @Autowired
    private MqttClientOnlineCheckService mqttClientOnlineCheckService;
    @Autowired
    private MqttCloudToScreenMessageResponseService mqttCloudToScreenMessageResponseService;


    public void handlerRequest(ContactScreenMqttRequest request) {

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_CLOUD_TO_SCREEN.getTopic().concat(request.getHeader().getScreenMac()), JSON.toJSONString(request), QosEnumConst.QOS_0);

        log.info("[下发外部mqtt消息执行]:消息类别:[{}],外部消息编号:[{}],消息体:{}",
                request.getHeader().getName(), request.getHeader().getMessageId(), JSON.toJSONString(request));

    }

    public void sendMsg(ContactScreenDomain message, String operateName) {

        ContactScreenMqttRequest payload = buildRequestData(message);
        // 暂时不做检查是否在线
//        Boolean onLineFlag = mqttClientOnlineCheckService.checkClientOnline(payload.getHeader().getScreenMac());
//        if (!onLineFlag) {
//            mqttCloudToScreenMessageResponseService.responseErrorMsg(message.getData().getScreenMac(),
//                    message.getData().getMessageId(), operateName, message.getOuterMessageId(),
//                    ErrorCodeEnumConst.MQTT_CLIENT_ERROR.getMsg(), ErrorCodeEnumConst.MQTT_CLIENT_ERROR.getCode());
//            return;
//        }
        // 通过mqtt下发到大屏
        handlerRequest(payload);

        // 发送完后修改  发送时间：当前时间戳，发送次数+1
        message.setSendTime(System.currentTimeMillis() + TaskConst.TIME_OUT_MILLISECONDS);
        message.setRetryTimes(message.getRetryTimes() + 1);

        // 下发完毕后放置到超时队列，该队列过一定间隔时间后，判断是否下发成功，若不成功，走原下发逻辑，继续执行下发
        timeoutService.addTimeoutTask(message);
    }


    public ContactScreenMqttRequest buildRequestData(ContactScreenDomain message) {
        ContactScreenMqttRequest result = null;

        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .screenMac(message.getData().getScreenMac())
                .messageId(message.getOuterMessageId()).name(message.getOperateName()).build();


        if (StringUtils.equals(message.getOperateName(), ContactScreenNameEnum.DEVICE_WRITE.getCode())) {

            ScreenMqttDeviceControlDTO deviceControlDTO = (ScreenMqttDeviceControlDTO) message.getData();

            List<ScreenDeviceAttributeDTO> data = deviceControlDTO.getData();
            List<ContactScreenDeviceAttribute> payloadAttributes = data.stream().map(i -> {
                ContactScreenDeviceAttribute contactScreenDeviceAttribute = new ContactScreenDeviceAttribute();
                BeanUtils.copyProperties(i, contactScreenDeviceAttribute);
                contactScreenDeviceAttribute.setAttrTag(i.getCode());
                contactScreenDeviceAttribute.setAttrValue(i.getValue());
                return contactScreenDeviceAttribute;
            }).collect(Collectors.toList());
            DeviceWritePayloadData writePayloadData = DeviceWritePayloadData.builder().deviceSn(deviceControlDTO.getDeviceSn()).productCode(deviceControlDTO.getProductCode())
                    .items(payloadAttributes).build();

            DeviceWritePayload payload = DeviceWritePayload.builder()
                    .data(writePayloadData).build();

            result = ContactScreenMqttRequest.builder().header(header).payload(payload).build();
        } else if (StringUtils.equals(message.getOperateName(), ContactScreenNameEnum.DEVICE_STATUS_READ.getCode())) {

            ScreenMqttDeviceStatusReadDTO deviceStatusReadDTO = (ScreenMqttDeviceStatusReadDTO) message.getData();


            DeviceStatusReadRequestPayloadData statusReadRequestPayloadData = DeviceStatusReadRequestPayloadData.builder()
                    .deviceSn(deviceStatusReadDTO.getDeviceSn())
                    .productCode(deviceStatusReadDTO.getProductCode()).build();

            DeviceStatusReadRequestPayload payload = DeviceStatusReadRequestPayload.builder()
                    .data(statusReadRequestPayloadData).build();
            result = ContactScreenMqttRequest.builder().header(header).payload(payload).build();

        } else if (StringUtils.equals(message.getOperateName(), ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode())) {

            ScreenMqttConfigUpdateDTO screenConfigUpdateDTO = (ScreenMqttConfigUpdateDTO) message.getData();

            FamilyConfigUpdatePayloadData familyConfigUpdatePayloadData = FamilyConfigUpdatePayloadData.builder()
                    .updateType(screenConfigUpdateDTO.getUpdateType()).build();

            FamilyConfigUpdatePayload payload = FamilyConfigUpdatePayload.builder()
                    .data(familyConfigUpdatePayloadData).build();

            result = ContactScreenMqttRequest.builder().header(header).payload(payload).build();

        } else if (StringUtils.equals(message.getOperateName(), ContactScreenNameEnum.FAMILY_SCENE_SET.getCode())) {

            ScreenMqttSceneControlDTO sceneControlDTO = (ScreenMqttSceneControlDTO) message.getData();
            FamilySceneSetPayloadData familySceneSetPayloadData = FamilySceneSetPayloadData.builder()
                    .sceneId(sceneControlDTO.getSceneId()).sceneNo(sceneControlDTO.getSceneNo()).build();
            FamilySceneSetPayload payload = FamilySceneSetPayload.builder()
                    .data(familySceneSetPayloadData).build();
            result = ContactScreenMqttRequest.builder().header(header).payload(payload).build();
        }

        return result;
    }

}
