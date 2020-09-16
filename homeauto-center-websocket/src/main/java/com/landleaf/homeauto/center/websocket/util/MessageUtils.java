package com.landleaf.homeauto.center.websocket.util;

import com.alibaba.fastjson.JSON;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSocket消息工具类
 *
 * @author Yujiumin
 * @version 2020/8/6
 */
public class MessageUtils {

    /**
     * 发送文本消息
     *
     * @param message 文本消息
     */
    public static void sendMessage(WebSocketSession session, Object message) {
        try {
            TextMessage textMessage = new TextMessage(JSON.toJSONString(message));
            session.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
