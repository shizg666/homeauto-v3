package com.landleaf.homeauto.contact.screen.controller.outer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.handle.HomeAutoRequestDispatch;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 大屏对云端的mqtt通信入口
 *
 * @author wenyilu
 */
@Slf4j
@MqttTopic(topic = "/screen/service/screen/to/cloud/#", wildcard = CommonConst.WildcardConst.LEVEL_WITH_ANY, omitted = false)
public class ContactScreenOuterMqttFromEntrance extends MessageBaseHandle {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HomeAutoRequestDispatch homeAutoRequestDispatch;

    @Override
    public void handle(String topic, MqttMessage message) {
        if (!redisUtils.getLock(RedisCacheConst.CONTACT_SCREEN_MQTT_SYNC_LOCK__SCREEN_CLOUD.concat(String.valueOf(message.getId())),
                RedisCacheConst.COMMON_EXPIRE)) {
            return;
        }
        try {
            String data = new String(message.getPayload());
            log.info("大屏==>MQTT==>云端,请求参数:{}", data);

            // 获取通用header信息，再交由具体类处理
            JSONObject jsonObject = JSON.parseObject(data, JSONObject.class);

            ContactScreenHeader header = JSON.parseObject(JSON.toJSONString(jsonObject.get("header")), ContactScreenHeader.class);

            ContactScreenContext.setContext(header);

            handleRequest(JSON.toJSONString(jsonObject.get("payload")));
        } finally {
            // 这里有个细节要注意,这里处理已经是线程内部,多线程任务分发在外部,否则线程变量不可用
            ContactScreenContext.remove();
        }

        redisUtils.del(RedisCacheConst.CONTACT_SCREEN_MQTT_SYNC_LOCK__SCREEN_CLOUD.concat(String.valueOf(message.getId())));
    }


    private void handleRequest(String payload) {

        String requestName = ContactScreenContext.getContext().getName();

        ContactScreenNameEnum screenNameEnum = ContactScreenNameEnum.getByCode(requestName);

        homeAutoRequestDispatch.dispatch(payload, screenNameEnum);

    }
}