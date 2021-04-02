package com.landleaf.homeauto.contact.screen.controller.inner.procedure.response;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 上行==》控制场景响应
 *
 * @author wenyilu
 */
@Component
public class FamilySceneControlResponseRocketMqProcedure extends AbstractResponseRocketMqProcedure {

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void procedureMessage(ScreenMqttResponseBaseDTO screenResponseBaseDTO) {
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER, RocketMqConst.TAG_FAMILY_SCENE_SET, JSON.toJSONString(screenResponseBaseDTO));
    }
}
