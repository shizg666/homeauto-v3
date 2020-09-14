package com.landleaf.homeauto.center.adapter.listener.screen.ack;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.adapter.service.AdapterAckMessageService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttDeviceControlResponseDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 控制设备响应
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER, tags = RocketMqConst.TAG_DEVICE_WRITE)
@Slf4j
public class ScreenDeviceWriteAckRocketMqConsumer extends AbstractMQMsgProcessor {


    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AdapterAckMessageService adapterAckMessageService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        String messageId = null;

        ScreenMqttDeviceControlResponseDTO requestDto = null;
        try {
            String msgBody = new String(message.getBody(), "utf-8");

            requestDto = JSON.parseObject(msgBody, ScreenMqttDeviceControlResponseDTO.class);
            messageId = requestDto.getMessageId();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (StringUtil.isEmpty(messageId) || !redisUtils.getLock(RedisCacheConst.ADAPTER_ROCKET_MQ_FROM_CONTACT_SCREEN_DEVICE_WRITE_ACK_SYNC_LOCK.concat(String.valueOf(messageId)),
                RedisCacheConst.COMMON_EXPIRE)) {
            log.error("[接收到ack消息][消息编号]:{},重复消费或messageId为空",messageId);

            return result;
        }
        try {
            String key = RedisCacheConst.ADAPTER_MSG_REQUEST_CONTACT_SCREEN.concat(requestDto.getMessageId());
            Object o = redisUtils.get(key);
            redisUtils.del(key);
            if (o != null) {
                AdapterDeviceControlDTO origin = JSON.parseObject(JSON.toJSONString(o), AdapterDeviceControlDTO.class);
                AdapterDeviceControlAckDTO ackDTO = new AdapterDeviceControlAckDTO();
                BeanUtils.copyProperties(origin, ackDTO);
                ackDTO.setCode(requestDto.getCode());
                ackDTO.setMessage(requestDto.getMessage());
                ackDTO.setSource(origin.getSource());
                adapterAckMessageService.dealMsg(ackDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        redisUtils.del(RedisCacheConst.ADAPTER_ROCKET_MQ_FROM_CONTACT_SCREEN_DEVICE_WRITE_ACK_SYNC_LOCK.concat(String.valueOf(messageId)));

        return result;
    }

}
