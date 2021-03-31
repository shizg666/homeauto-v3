package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 上行==》状态变化上报
 *
 * @author wenyilu
 */
@Component
public class DeviceStatusChangeRocketMqProcedure extends AbstractUploadRocketMqProcedure {

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO) {

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER, RocketMqConst.DEVICE_STATUS_UPLOAD, JSON.toJSONString(screenUploadBaseDTO));

    }
}
