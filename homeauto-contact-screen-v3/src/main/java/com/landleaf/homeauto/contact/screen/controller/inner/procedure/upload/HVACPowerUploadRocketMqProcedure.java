package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 暖通功率往上报
 *
 * @author wenyilu
 */
@Component("hVACPowerUploadRocketMqProcedure")
public class HVACPowerUploadRocketMqProcedure extends AbstractUploadRocketMqProcedure {

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO) {

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER, RocketMqConst.HVAC_POWER_UPLOAD, JSON.toJSONString(screenUploadBaseDTO));

    }
}
