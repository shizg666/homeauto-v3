package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
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
@Component
public class RedisMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            String bodyString = new String(message.getBody(), StandardCharsets.UTF_8);
            MessageModel messageModel = JSON.parseObject(bodyString, MessageModel.class);
            String familyId = messageModel.getFamilyId();
            WebSocketSession webSocketSession = WebSocketSessionContext.get(familyId);
            if (!Objects.isNull(webSocketSession)) {
                AppMessage appMessage = new AppMessage(messageModel.getMessageCode(), messageModel.getMessage());
                webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(appMessage)));
            }
            log.info("家庭[{}]不在线,推送失败", familyId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
