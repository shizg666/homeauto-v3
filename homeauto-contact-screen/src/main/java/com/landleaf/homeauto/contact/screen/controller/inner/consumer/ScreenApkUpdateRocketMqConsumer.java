package com.landleaf.homeauto.contact.screen.controller.inner.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttApkUpdateDTO;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 内部服务传来的升级大屏apk通知
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, tags = RocketMqConst.TAG_SCREEN_APK_UPDATE)
@Slf4j
public class ScreenApkUpdateRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private MqttCloudToScreenMessageService mqttCloudToScreenMessageService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        try {
            String msgBody = new String(message.getBody(), "utf-8");
            log.info("收到消息:{}", msgBody);
            //解析消息
            ScreenMqttApkUpdateDTO requestDto = JSON.parseObject(msgBody, ScreenMqttApkUpdateDTO.class);

            ContactScreenDomain messageDomain = mqttCloudToScreenMessageService.buildMessage(requestDto, ContactScreenNameEnum.SCREEN_APK_UPDATE.getCode());

            mqttCloudToScreenMessageService.addTask(messageDomain);

        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }


}
