package com.landleaf.homeauto.center.adapter.listener.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.adapter.service.AdapterRequestMessageService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageSourceEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 内部服务传来的对场景控制命令
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_APP_TO_CENTER_ADAPTER, tags = RocketMqConst.TAG_FAMILY_SCENE_SET)
@Slf4j
public class FamilySceneControlRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private AdapterRequestMessageService adapterRequestMessageService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        String messageId = null;

        AdapterSceneControlDTO requestDto = null;
        try {
            String msgBody = new String(message.getBody(), "utf-8");

            requestDto = JSON.parseObject(msgBody, AdapterSceneControlDTO.class);
            messageId=requestDto.getMessageId();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (StringUtil.isEmpty(messageId) ||!redisUtils.getLock(RedisCacheConst.ADAPTER_ROCKET_MQ_FROM_APP_SCENE_SET_SYNC_LOCK.concat(String.valueOf(messageId)),
                RedisCacheConst.COMMON_EXPIRE)) {
            log.error("[接收到mq下发消息][消息编号]:{},重复消费或messageId为空",messageId);
            return result;
        }

        try {
            requestDto.setSource(AdapterMessageSourceEnum.APP_REQUEST.getName());

            adapterRequestMessageService.dealMsg(requestDto);

        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        redisUtils.del(RedisCacheConst.ADAPTER_ROCKET_MQ_FROM_APP_SCENE_SET_SYNC_LOCK.concat(String.valueOf(messageId)));

        return result;
    }

}
