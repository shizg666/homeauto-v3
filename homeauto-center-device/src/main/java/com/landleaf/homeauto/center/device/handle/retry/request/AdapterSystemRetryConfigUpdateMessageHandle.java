package com.landleaf.homeauto.center.device.handle.retry.request;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;


/**
 * 将发送系统重试配置更新的请求发送到到Adapter模块数据处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class AdapterSystemRetryConfigUpdateMessageHandle implements Observer {

    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;
    @Autowired
    private IAdapterRequestMsgLogService adapterRequestMsgLogService;

    @Override
    @Async("bridgeDealRequestMessageExecute")
    public void update(Observable o, Object arg) {
        AdapterMessageBaseDTO message = (AdapterMessageBaseDTO) arg;
        // 走下面处理逻辑

        // 组装数据
        String messageName = message.getMessageName();

        if (arg != null) {
            // 发送数据
            try {
                mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_SYSTEM_RETRY_TO_CENTER_ADAPTER, messageName, JSON.toJSONString(arg));
                // 记录消息id

                log.info("[下发mq消息]:消息类别:[{}],消息编号:[{}],消息体:{}",
                        message.getMessageName(), message.getMessageId(), message);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            try {
                //修改重试记录
                adapterRequestMsgLogService.updateRecordRetry(message.getMessageId(),message.getFamilyId());

            } catch (Exception e) {
                log.error("更新失败记录重试次数及时间异常，装作没看见....");
            }
        }
    }

}
