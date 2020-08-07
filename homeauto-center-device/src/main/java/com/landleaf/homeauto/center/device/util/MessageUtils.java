package com.landleaf.homeauto.center.device.util;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author Yujiumin
 * @version 2020/8/6
 */
public class MessageUtils {

    /**
     * 发送文本消息
     *
     * @param message 文本消息
     */
    public static void sendTextMessage(WebSocketSession session, String message) {
        try {
            TextMessage textMessage = new TextMessage(message);
            session.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
