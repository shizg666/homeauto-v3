package com.landleaf.homeauto.contact.screen.client.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenMqttResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.client.dto.payload.http.request.ApkVersionCheckRequestPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.CommonResponsePayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request.FamilyConfigUpdatePayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response.DeviceStatusReadRequestReplyData;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response.DeviceStatusReadRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.client.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.client.service.HttpRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 大屏对云端的mqtt通信入口
 *
 * @author wenyilu
 */
@Slf4j
@MqttTopic(topic = "/screen/service/cloud/to/screen/10.10.10.10.10.10", wildcard = CommonConst.WildcardConst.LEVEL_WITH_ANY, omitted = false)
public class ContactScreenOuterMqttFromEntrance extends MessageBaseHandle {
    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;
    @Autowired
    private HttpRequestService httpRequestService;


    @Override
    public void handle(String topic, MqttMessage message) {
        try {
            String data = new String(message.getPayload());
            log.info("云端==>MQTT==>大屏,请求参数:{}", data);

            // 获取通用header信息，再交由具体类处理
            JSONObject jsonObject = JSON.parseObject(data, JSONObject.class);

            ContactScreenHeader header = JSON.parseObject(JSON.toJSONString(jsonObject.get("header")), ContactScreenHeader.class);

            handleRequest(JSON.toJSONString(jsonObject.get("payload")), header);
        } finally {
            // 这里有个细节要注意,这里处理已经是线程内部,多线程任务分发在外部,否则线程变量不可用
        }

    }


    private void handleRequest(String payload, ContactScreenHeader header) {

        ContactScreenMqttResponse response = new ContactScreenMqttResponse();
        String ackCode = header.getAckCode();
        if (StringUtils.equals(ackCode, "1")) {
            // 不需要响应
            return;
        }
        String name = header.getName();
        if (StringUtils.equals(name, "DeviceWrite") || StringUtils.
                equals(name, "FamilySceneSet")
                || StringUtils.equals(name, "FamilyConfigUpdate") || StringUtils.equals(name, "ScreenApkUpdate")
        ) {
            CommonResponsePayload responsePayload = CommonResponsePayload.builder().code(ContactScreenErrorCodeEnumConst.SUCCESS.getCode())
                    .message(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg()).build();

            response.setPayload(responsePayload);
            response.setHeader(header);

        } else if (StringUtils.equals(name, "DeviceStatusRead")) {
            // 读取设备状态
            DeviceStatusReadRequestReplyPayload statusReadRequestReplyPayload = new DeviceStatusReadRequestReplyPayload();
            DeviceStatusReadRequestReplyData data = new DeviceStatusReadRequestReplyData();
            List<ContactScreenDeviceAttribute> items = Lists.newArrayList();
            for (int i = 0; i < 5; i++) {
                ContactScreenDeviceAttribute attribute = new ContactScreenDeviceAttribute();
                attribute.setCode(String.valueOf(i));
                attribute.setValue(String.valueOf(RandomUtils.nextInt()));
                items.add(attribute);
            }
            data.setItems(items);
            statusReadRequestReplyPayload.setData(data);
            statusReadRequestReplyPayload.setCode(200);
            data.setDeviceSn(String.valueOf(1));
            data.setProductCode("123");
            statusReadRequestReplyPayload.setMessage("成功");
            response.setPayload(statusReadRequestReplyPayload);
            response.setHeader(header);
        }
        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_SCREEN_TO_CLOUD.getTopic().concat("123"), JSON.toJSONString(response), QosEnumConst.QOS_0);

        if (StringUtils.equals(name, "FamilyConfigUpdate")) {
            // 配置更新通知,主动拉取
            FamilyConfigUpdatePayload configUpdatePayload = JSON.parseObject(payload, FamilyConfigUpdatePayload.class);
            String updateType = configUpdatePayload.getUpdateType();
            ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();
            requestDTO.setScreenMac(header.getScreenMac());
            ContactScreenHttpResponse contactScreenHttpResponse = null;
            if (StringUtils.equals(updateType, ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE.code)) {
                contactScreenHttpResponse = httpRequestService.floorRoomDeviceList(requestDTO);
            } else if (StringUtils.equals(updateType, ContactScreenConfigUpdateTypeEnum.SCENE_TIMING.code)) {
                contactScreenHttpResponse = httpRequestService.smartSceneTimingList(requestDTO);
            } else if (StringUtils.equals(updateType, ContactScreenConfigUpdateTypeEnum.NEWS.code)) {
                contactScreenHttpResponse = httpRequestService.newsList(requestDTO);
            } else if (StringUtils.equals(updateType, ContactScreenConfigUpdateTypeEnum.SCENE.code)) {
                contactScreenHttpResponse = httpRequestService.smartSceneList(requestDTO);
            } else if (StringUtils.equals(updateType, ContactScreenConfigUpdateTypeEnum.APK_UPDATE.code)) {
                ApkVersionCheckRequestPayload requestPayload = new ApkVersionCheckRequestPayload();
                requestPayload.setRequest("1.0.0");
                contactScreenHttpResponse = httpRequestService.apkVersionCheck(requestPayload, header.getScreenMac());
            }
            log.info("收到通知[{}],主动请求,获取数据:{}", name, JSON.toJSONString(contactScreenHttpResponse));

        }

    }

}