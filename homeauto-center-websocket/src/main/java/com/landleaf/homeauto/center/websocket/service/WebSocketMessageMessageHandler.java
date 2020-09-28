package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.center.websocket.rocketmq.annotation.Consumer;
import com.landleaf.homeauto.center.websocket.rocketmq.consumer.AbstractMessageHandler;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Redis消息监听器
 *
 * @author Yujiumin
 * @version 2020/9/21
 */
@Slf4j
@Service
@Consumer(group = "WEB_SOCKET", topic = RocketMqConst.TOPIC_WEBSOCKET_TO_APP)
public class WebSocketMessageMessageHandler extends AbstractMessageHandler {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(String[] keys, String message) {
        try {
            MessageModel messageModel = JSON.parseObject(message, MessageModel.class);
            String familyId = messageModel.getFamilyId();
            WebSocketSession webSocketSession = WebSocketSessionContext.get(familyId);
            if (!Objects.isNull(webSocketSession)) {
                AppMessage appMessage = new AppMessage(messageModel.getMessageCode(), messageModel.getMessage());
                try {
                    webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(appMessage)));
                    log.info("成功推送状态消息:{}",JSON.toJSONString(appMessage));
                } catch (IOException e) {
                    log.error("发送消息异常了,我又该肿么办....");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            log.info("家庭[{}]不在线,推送失败", familyId);
        } catch (Exception e) {
            log.error("消费消息,解析异常了,我又该肿么办....");
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
