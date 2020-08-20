package com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 场景状态变化往上报
 *
 * @author wenyilu
 */
@Component
public class ScreenSceneSetUploadRocketMqProcedure extends AbstractUploadRocketMqProcedure {

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void procedureMessage(ScreenMqttUploadBaseDTO screenUploadBaseDTO) {
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CONTACT_SCREENC_TO_ENTER_ADAPTER, RocketMqConst.SCREEN_APK_UPDATE_CHECK, JSON.toJSONString(screenUploadBaseDTO));

    }
}
