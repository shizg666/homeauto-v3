package com.landleaf.homeauto.contact.screen.controller.inner.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceControlDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.MESSAGE_EXPIRE;

/**
 * 内部服务传来的对设备的控制命令
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, tags = RocketMqConst.TAG_DEVICE_WRITE)
@Slf4j
public class DeviceWriteRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private MqttCloudToScreenMessageService mqttCloudToScreenMessageService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);

        String msgBody = null;
        String messageId = null;
        ScreenMqttDeviceControlDTO requestDto = null;
        try {
            msgBody = new String(message.getBody(), "utf-8");
            //解析消息
            requestDto = JSON.parseObject(msgBody, ScreenMqttDeviceControlDTO.class);
            Long operateTime = requestDto.getOperateTime();
            if(operateTime+MESSAGE_EXPIRE<System.currentTimeMillis()){
                log.error("[内部执行消息超过3分钟,已失效不再执行]");
                return result;
            }
            messageId=requestDto.getMessageId();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (StringUtil.isEmpty(messageId) ||!redisUtils.getLock(RedisCacheConst.CONTACT_SCREEN_ROCKET_MQ_FROM_ADAPTER_DEVICE_WRITE_SYNC_LOCK.concat(String.valueOf(messageId)),
                RedisCacheConst.COMMON_EXPIRE)) {
            log.error("[接收到内部mq消息][消息编号]:{},重复消费或messageId为空",messageId);
            return result;
        }

        try {
            ContactScreenDomain messageDomain = mqttCloudToScreenMessageService.buildMessage(requestDto, ContactScreenNameEnum.DEVICE_WRITE.getCode());

            log.info("[接收到内部mq消息]:消息类别:[{}],内部消息编号:[{}],外部消息编号:[{}],消息体:{}",
                    messageDomain.getOperateName(), messageDomain.getData().getMessageId(), messageDomain.getOuterMessageId()
                    , msgBody);

            mqttCloudToScreenMessageService.addTask(messageDomain);

        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        redisUtils.del(RedisCacheConst.CONTACT_SCREEN_ROCKET_MQ_FROM_ADAPTER_DEVICE_WRITE_SYNC_LOCK.concat(String.valueOf(messageId)));
        return result;
    }

}
