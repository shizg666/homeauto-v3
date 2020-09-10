package com.landleaf.homeauto.center.device.listener.adapter.ack;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.device.service.bridge.retry.ISystemConfigUpdateRetryAckService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 收到 adapter 配置更新通知
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_ADAPTER_TO_SYSTEM_RETRY,
        tags = RocketMqConst.TAG_FAMILY_CONFIG_UPDATE)
@Slf4j
public class BridgeFamilyRetryConfigUpdateRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private ISystemConfigUpdateRetryAckService systemConfigUpdateRetryAckService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        try {
            log.info("[配置更新重试请求ack]");
            String msgBody = new String(message.getBody(), "utf-8");

            AdapterConfigUpdateAckDTO ackDTO = JSON.parseObject(msgBody, AdapterConfigUpdateAckDTO.class);

            systemConfigUpdateRetryAckService.dealMsg(ackDTO);

        } catch (Exception e) {
            //本程序异常，无需通知MQ重复下发消息
            e.printStackTrace();
        }

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }


}
