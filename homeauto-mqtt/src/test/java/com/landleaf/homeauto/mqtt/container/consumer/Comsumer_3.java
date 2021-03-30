package com.landleaf.homeauto.mqtt.container.consumer;

import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.client.TransportClient;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;


public class Comsumer_3 {

    @Test
    public void testClient() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
    RsocketClientSession clientSession= TransportClient.create("127.0.0.1",1884)
              .heart(10000)
              .protocol(ProtocolType.MQTT)
              .ssl(false)
              .log(true)
              .clientId("Comsumer_3")
                .password("12")
            .username("123")
            .willMessage("123")
            .willTopic("/lose")
              .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
              .messageAcceptor((topic,msg)->{
                    System.out.println(topic+":"+new String(msg));
               })
              .connect()
              .block();
    Thread.sleep(5000);
        clientSession.sub("test").subscribe();
        latch.await();



    }

}
