package com.landleaf.homeauto.center.device.handle.req;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;


/**
 * 将发送App的请求发送到到Adapter模块数据处理类
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class AdapterRequestMessageHandle implements Observer {

    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    @Async("bridgeDealRequestMessageExecute")
    public void update(Observable o, Object arg) {
        AdapterMessageBaseDTO message = (AdapterMessageBaseDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()) {

            // 组装数据
            String messageName = message.getMessageName();

            if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName())) {
                // 控制设备ack   TODO ack时其它逻辑处理

            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_STATUS_READ.getName())) {
                // 读取状态

            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_CONFIG_UPDATE.getName())) {


            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_SCENE_SET.getName())) {
                // 控制场景
            }else {
                return;
            }

            if (arg != null) {
                // 发送数据
                try {
                    mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_APP_TO_CENTER_ADAPTER, messageName, JSON.toJSONString(arg));
                    // 记录消息id

                    log.info("[下发mq消息]:消息类别:[{}],消息编号:[{}],消息体:{}",
                            message.getMessageName(), message.getMessageId(), message);

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}
