package com.landleaf.homeauto.center.device.handle.request;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.*;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 将发送到Adapter模块数据处理类
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class ContactScreenRequestMessageHandle implements Observer {

    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Async("BridgeDealRequestMessageExecute")
    public void update(Observable o, Object arg) {
        AdapterMessageBaseDTO message = (AdapterMessageBaseDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()) {

            // 组装数据
            String messageName = message.getMessageName();

            ScreenMqttBaseDTO sendData = null;
            if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName())) {
                // 控制设备
                AdapterDeviceControlDTO dto = (AdapterDeviceControlDTO) message;
                sendData = new ScreenMqttDeviceControlDTO();
                BeanUtils.copyProperties(dto, sendData);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_STATUS_READ.getName())) {
                // 读取状态
                AdapterDeviceStatusReadDTO dto = (AdapterDeviceStatusReadDTO) message;
                sendData = new ScreenMqttDeviceStatusReadDTO();
                BeanUtils.copyProperties(dto, sendData);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_CONFIG_UPDATE.getName())) {
                // 配置更新
                AdapterConfigUpdateDTO dto = (AdapterConfigUpdateDTO) message;
                sendData = new ScreenMqttConfigUpdateDTO();
                BeanUtils.copyProperties(dto, sendData);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_SCENE_SET.getName())) {
                // 控制场景
                AdapterSceneControlDTO dto = (AdapterSceneControlDTO) message;
                sendData = new ScreenMqttSceneControlDTO();
                BeanUtils.copyProperties(dto, sendData);
            }
            if (sendData != null) {
                sendData.setMessageId(message.getMessageId());
                sendData.setScreenMac(message.getTerminalMac());
                // 发送数据
                try {
                    mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_APP_TO_CENTER_ADAPTER, messageName, JSON.toJSONString(sendData));
                    // 记录消息id
                    String messageId = message.getMessageId();
                    String key = RedisCacheConst.ADAPTER_MSG_REQUEST_CONTACT_SCREEN.concat(messageId);

                    redisUtils.set(key, message, RedisCacheConst.COMMON_EXPIRE);
                    
                    log.info("[下发mq消息]:消息类别:[{}],消息编号:[{}],消息体:{}",
                            message.getMessageName(), message.getMessageId(), message);

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }


        }
    }

}
