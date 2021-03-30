package com.landleaf.homeauto.mqtt.transport.client.handler;

import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.config.RsocketClientConfig;
import com.landleaf.homeauto.mqtt.transport.DirectHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ClientMessageRouter {


    private final RsocketClientConfig config;

    private final DirectHandlerAdaptor directHandlerAdaptor;

    public ClientMessageRouter(RsocketClientConfig config) {
        this.config=config;
        this.directHandlerAdaptor= DirectHandlerFactory::new;
    }

    public void handler(MqttMessage message, TransportConnection connection) {
        if(message.decoderResult().isSuccess()){
            DirectHandler handler=directHandlerAdaptor.handler(message.fixedHeader().messageType()).loadHandler();
            log.info("accept message  info{}",message);
             handler.handler(message,connection,config);
        }
        else {
            log.error("accept message  error{}",message.decoderResult().toString());
        }
    }

}
