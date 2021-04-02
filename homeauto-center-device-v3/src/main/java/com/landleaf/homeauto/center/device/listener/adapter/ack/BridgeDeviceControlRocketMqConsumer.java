package com.landleaf.homeauto.center.device.listener.adapter.ack;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.device.service.bridge.BridgeAckMessageService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 收到 adapter 设备控制处理
 *
 * @author zhanghongbin
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_ADAPTER_TO_APP,
        tags = RocketMqConst.TAG_DEVICE_WRITE)
@Slf4j
public class BridgeDeviceControlRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private BridgeAckMessageService bridgeAckMessageService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        try {
            String msgBody = new String(message.getBody(), "utf-8");
            AdapterDeviceControlAckDTO deviceControlAckDTO = JSON.parseObject(msgBody, AdapterDeviceControlAckDTO.class);

            bridgeAckMessageService.dealMsg(deviceControlAckDTO);

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
