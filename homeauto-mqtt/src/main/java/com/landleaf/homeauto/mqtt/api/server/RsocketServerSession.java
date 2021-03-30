package com.landleaf.homeauto.mqtt.api.server;

import com.landleaf.homeauto.mqtt.api.TransportConnection;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RsocketServerSession  extends Disposable {


  Mono<List<TransportConnection>> getConnections();

  Mono<Void> closeConnect(String clientId);

}
