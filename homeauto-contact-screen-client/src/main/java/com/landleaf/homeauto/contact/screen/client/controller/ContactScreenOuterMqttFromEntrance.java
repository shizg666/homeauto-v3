package com.landleaf.homeauto.contact.screen.client.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenMqttResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.CommonResponsePayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response.DeviceStatusReadRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.client.enums.ContactScreenErrorCodeEnumConst;
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
@MqttTopic(topic = "/screen/service/cloud/to/screen/123", wildcard = CommonConst.WildcardConst.LEVEL_WITH_ANY, omitted = false)
public class ContactScreenOuterMqttFromEntrance extends MessageBaseHandle {
    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;


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
        if(StringUtils.equals(ackCode,"1")){
            // 不需要响应
            return;
        }
        String name = header.getName();
        if (StringUtils.equals(name, "DeviceWrite") || StringUtils.equals(name, "FamilySceneSet")
                || StringUtils.equals(name, "FamilyConfigUpdate") || StringUtils.equals(name, "ScreenApkUpdate")
        ) {
            CommonResponsePayload responsePayload = CommonResponsePayload.builder().code(ContactScreenErrorCodeEnumConst.SUCCESS.getCode())
                    .message(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg()).build();

            response.setPayload(responsePayload);
            response.setHeader(header);


        } else if (StringUtils.equals(name, "DeviceStatusRead")) {
            // 读取设备状态
            DeviceStatusReadRequestReplyPayload statusReadRequestReplyPayload = new DeviceStatusReadRequestReplyPayload();
            List<ContactScreenDeviceAttribute> data = Lists.newArrayList();
            for (int i = 0; i < 5; i++) {
                ContactScreenDeviceAttribute attribute = new ContactScreenDeviceAttribute();
                attribute.setCode(String.valueOf(i));
                attribute.setValue(String.valueOf(RandomUtils.nextInt()));
                data.add(attribute);
            }
            statusReadRequestReplyPayload.setData(data);
            statusReadRequestReplyPayload.setCode(200);
            statusReadRequestReplyPayload.setDeviceSn(String.valueOf(1));
            statusReadRequestReplyPayload.setMessage("成功");
            response.setPayload(statusReadRequestReplyPayload);
            response.setHeader(header);
        }
        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_SCREEN_TO_CLOUD.getTopic().concat("123"), JSON.toJSONString(response), QosEnumConst.QOS_0);

    }

}