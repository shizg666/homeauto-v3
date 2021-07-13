package com.landleaf.homeauto.center.adapter.listener.screen.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.adapter.service.AdapterUploadMessageService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterHVACPowerUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttHVACPowerUploadDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.consumer.RocketMQConsumeService;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 收到 功率 上报
 *
 * @author zhanghongbin
 */
@RocketMQConsumeService(topic = RocketMqConst.TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER, tags = RocketMqConst.HVAC_POWER_UPLOAD)
@Slf4j
public class ScreenHVACPowerUploadRocketMqConsumer extends AbstractMQMsgProcessor {

    @Autowired
    private AdapterUploadMessageService adapterUploadMessageService;

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        try {
            String msgBody = new String(message.getBody(), "utf-8");

            ScreenMqttHVACPowerUploadDTO requestDto = JSON.parseObject(msgBody, ScreenMqttHVACPowerUploadDTO.class);
            // 转换为adapter的uploadDTO
            AdapterHVACPowerUploadDTO uploadDTO = new AdapterHVACPowerUploadDTO();
            BeanUtils.copyProperties(requestDto, uploadDTO);
            uploadDTO.setTerminalMac(requestDto.getScreenMac());
            uploadDTO.setMessageName(AdapterMessageNameEnum.HVAC_POWER_UPLOAD.getName());
            uploadDTO.setProductCode(String.valueOf(requestDto.getProductCode()));

            adapterUploadMessageService.dealMsg(uploadDTO);

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
