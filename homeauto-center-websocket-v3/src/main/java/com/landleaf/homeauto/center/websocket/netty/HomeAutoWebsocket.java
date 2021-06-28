package com.landleaf.homeauto.center.websocket.netty;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author wenyilu
 */

    @Slf4j
    @ServerEndpoint(path = "/websocket/endpoint/{familyId}", port = "10017")
    public class HomeAutoWebsocket {
    private static final String SECRET = "LANDLEAF-HOMEAUTO";

    @BeforeHandshake
    public boolean beforeHandshake(Session session, HttpHeaders headers, @PathVariable String familyId) throws Exception {
//        for (Map.Entry<String, String> header : headers) {
//            String key = header.getKey();
//            if(StringUtils.isNotEmpty(key)&& StringUtils.equals(key,CommonConst.AUTHORIZATION)){
//                String authorization = header.getValue();
//                String s = DigestUtil.md5Hex(SECRET);
//                if (DigestUtil.md5Hex(SECRET).equalsIgnoreCase(authorization)) {
//                    return true;
//                }
//            }
//        }
//        return false;
        // 暂不做校验
        session.setAttribute("familyId", familyId);
        return true;
    }

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @PathVariable String familyId) {
        String id = session.id().asLongText();
        Channel channel = session.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        log.info("我是新来的客户端，我想建立链接，请审批！！！地址:{},familyId:{},ChannelId:{}", JSON.toJSONString(socketAddress), familyId, id);
        log.info("新来的小伙儿，就留你了，申请通过!!");
        WebSocketSessionContext.put(familyId, id, session);
        //顺便清理家庭无用连接
        WebSocketSessionContext.clearLink(familyId);

    }

    @OnClose
    public void onClose(Session session, @PathVariable String familyId) throws IOException {
        log.error("链接关闭了，我自由了!!sessionId:{}", session.id().asLongText());
        WebSocketSessionContext.remove(familyId, session);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathVariable String familyId) {
        log.error("我感受到了异常，我要释放了!!sessionId:{},异常：{}", session.id().asLongText(), throwable.getMessage(), throwable);
        WebSocketSessionContext.remove(familyId, session);
        session.close();
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathVariable String familyId) {
        try {
            AppMessage appMessageModel = JSON.parseObject(message, AppMessage.class);
        } catch (Exception e) {
            log.error("消息转换异常,我该肿么办....");
        }
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    /**
     * 扩展部分,读写空闲作某些操作
     *
     * @param session
     * @param evt
     */
    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
