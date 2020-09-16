package com.landleaf.homeauto.center.websocket;

import com.neovisionaries.ws.client.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author Yujiumin
 * @version 2020/9/16
 */
@SpringBootTest
public class WebSocketTest {

    @Test
    public void heartbeat() throws Exception {
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        WebSocket webSocket = webSocketFactory.createSocket("ws://127.0.0.1:10017/websocket/connect/a7c6e89a01bc4107b67a99fdd1cd0805", 3000);
        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                System.out.println(frame.getPayloadText());
            }
        });
        webSocket.addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
        webSocket.connect();
        webSocket.flush();
    }

}
