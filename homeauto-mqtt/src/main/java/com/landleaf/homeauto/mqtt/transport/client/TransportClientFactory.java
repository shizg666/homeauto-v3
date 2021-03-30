package com.landleaf.homeauto.mqtt.transport.client;

import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.config.RsocketClientConfig;
import com.landleaf.homeauto.mqtt.protocol.ProtocolFactory;
import com.landleaf.homeauto.mqtt.transport.client.connection.RsocketClientConnection;
import reactor.core.publisher.Mono;


public class TransportClientFactory {

    private ProtocolFactory protocolFactory;


    private RsocketClientConfig clientConfig;


    public TransportClientFactory(){
        protocolFactory = new ProtocolFactory();
    }


    public Mono<RsocketClientSession> connect(RsocketClientConfig config) {
        this.clientConfig=config;
        return  Mono.from(protocolFactory.getProtocol(ProtocolType.valueOf(config.getProtocol()))
                .get().getTransport().connect(config)).map(this::wrapper).doOnError(config.getThrowableConsumer());
    }


    private RsocketClientSession wrapper(TransportConnection connection){
        return  new RsocketClientConnection(connection,clientConfig);
    }

}
