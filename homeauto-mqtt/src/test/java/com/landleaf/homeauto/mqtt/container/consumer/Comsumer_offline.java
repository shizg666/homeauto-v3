package com.landleaf.homeauto.mqtt.container.consumer;

import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.client.TransportClient;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class Comsumer_offline {

    @Test
    public void testClient() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        RsocketClientSession clientSession= TransportClient.create("127.0.0.1",1884)
                .heart(10000)
                .protocol(ProtocolType.MQTT)
                .ssl(false)
                .log(false)
                .clientId("lose")
                .password("12")
                .username("123")
                .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
                .messageAcceptor((topic,msg)->{
                    System.out.println(topic+":"+new String(msg));
                })
                .connect()
                .block();
        Thread.sleep(5000);
        clientSession.sub("/lose").subscribe();
        latch.await();



    }

}
