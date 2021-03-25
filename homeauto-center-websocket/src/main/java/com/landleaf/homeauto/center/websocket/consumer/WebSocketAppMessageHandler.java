package com.landleaf.homeauto.center.websocket.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.center.websocket.rocketmq.annotation.Consumer;
import com.landleaf.homeauto.center.websocket.rocketmq.consumer.AbstractMessageHandler;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yeauty.pojo.Session;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Redis消息监听器
 *
 * @author Yujiumin
 * @version 2020/9/21
 */
@Slf4j
@Service
@Consumer(group = "WEB_SOCKET", topic = RocketMqConst.TOPIC_WEBSOCKET_TO_APP)
public class WebSocketAppMessageHandler extends AbstractMessageHandler {

    @Autowired
    private Executor wsSendMsgExecutePool;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(String[] keys, String message) {
        log.info("app_消费消息:{}", JSON.toJSONString(message));
        try {
            MessageModel messageModel = JSON.parseObject(message, MessageModel.class);
            String familyId = messageModel.getFamilyId();

            wsSendMsgExecutePool.execute(new MessageHandleRunnable(messageModel,familyId));

        } catch (Exception e) {
            log.error("消费消息,解析异常了,我又该肿么办....", e);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
