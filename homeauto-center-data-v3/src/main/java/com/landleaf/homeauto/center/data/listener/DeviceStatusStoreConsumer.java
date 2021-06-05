package com.landleaf.homeauto.center.data.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusCurrentService;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用来接收状态消息，然后存储
 *
 * @author wenyilu
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CENTER_DEVICE_TO_CENTER_DATA, tags = RocketMqConst.TAG_DEVICE_STATUS_TO_DATA)
@Slf4j
public class DeviceStatusStoreConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private IFamilyDeviceStatusHistoryService familyDeviceStatusHistoryService;
    @Autowired
    private IFamilyDeviceStatusCurrentService familyDeviceStatusCurrentService;
    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {

        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        List<DeviceStatusBO> requestDto = null;
        try {
            String msgBody = new String(message.getBody(), "utf-8");
            requestDto = JSON.parseArray(msgBody, DeviceStatusBO.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            familyDeviceStatusHistoryService.insertBatchDeviceStatus(requestDto, now);
            familyDeviceStatusCurrentService.insertBatchDeviceStatus(requestDto,now);
        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        return result;
    }


}
