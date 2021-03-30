package com.landleaf.homeauto.mqtt.transport.server.handler;

import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.config.RsocketServerConfig;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ServerMessageRouter {


    private final RsocketServerConfig config;

    private final DirectHandlerAdaptor directHandlerAdaptor;

    public ServerMessageRouter(RsocketServerConfig config) {
        this.config=config;
        this.directHandlerAdaptor= DirectHandlerFactory::new;
    }

    public void handler(MqttMessage message, TransportConnection connection) {
        if(message.decoderResult().isSuccess()){
            log.info("accept message channel {} info{}",connection.getConnection(),message);
            DirectHandler handler=directHandlerAdaptor.handler(message.fixedHeader().messageType()).loadHandler();
             handler.handler(message,connection,config);
        }
        else {
            log.error("accept message  error{}",message.decoderResult().toString());
        }
    }

}
