package com.landleaf.homeauto.center.device.listener.adapter.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.device.service.bridge.BridgeUploadMessageService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterScreenSceneSetUploadDTO;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 收到 adapter 场景上报处理
 *
 * @author zhanghongbin
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_ADAPTER_TO_APP, tags = RocketMqConst.SCREEN_SCENE_SET_UPLOAD)
@Slf4j
public class BridgeSceneSetUploadRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private BridgeUploadMessageService bridgeUploadMessageService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        try {
            String msgBody = new String(message.getBody(), "utf-8");

            //转换为adapter场景上报上报结构体
            AdapterScreenSceneSetUploadDTO uploadDTO = JSON.parseObject(msgBody, AdapterScreenSceneSetUploadDTO.class);

            bridgeUploadMessageService.dealMsg(uploadDTO);

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
