package com.landleaf.homeauto.contact.screen.controller.inner.procedure.response;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constance.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 控制设备响应结果
 *
 * @author wenyilu
 */
@Component
public class DeviceWriteResponseRocketMqProcedure extends AbstractResponseRocketMqProcedure {
    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void procedureMessage(ScreenMqttResponseBaseDTO screenResponseBaseDTO) {

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CONTACT_SCREENC_TO_ENTER_ADAPTER, RocketMqConst.TAG_DEVICE_WRITE, JSON.toJSONString(screenResponseBaseDTO));

    }
}
