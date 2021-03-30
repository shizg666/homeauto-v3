package com.landleaf.homeauto.mqtt.container;

import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.api.server.RsocketServerSession;
import com.landleaf.homeauto.mqtt.api.server.handler.MemoryMessageHandler;
import com.landleaf.homeauto.mqtt.common.annocation.ProtocolType;
import com.landleaf.homeauto.mqtt.transport.server.TransportServer;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ServerTest {

    @Test
    public void testServer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
          RsocketServerSession serverSession= TransportServer.create("192.168.13.205",1884)
                  .auth((s,p)->true)
                  .heart(100000)
                  .protocol(ProtocolType.MQTT)
                  .ssl(false)
                  .auth((username,password)->true)
                  .log(true)
                  .messageHandler(new MemoryMessageHandler())
                  .exception(throwable -> System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+throwable))
                  .start()
                  .block();
            serverSession.closeConnect("device-1").subscribe();// 关闭设备端
            List<TransportConnection> connections= serverSession.getConnections().block(); // 获取所有链接
        latch.await();



    }

}
