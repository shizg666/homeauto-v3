package com.landleaf.homeauto.center.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * WebSocket服务
 *
 * @author Yujiumin
 * @version 2020/8/10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class HomeAutoCenterWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeAutoCenterWebSocketApplication.class, args);
    }

}
