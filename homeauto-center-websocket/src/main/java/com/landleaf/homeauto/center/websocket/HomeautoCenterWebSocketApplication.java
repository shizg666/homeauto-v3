package com.landleaf.homeauto.center.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * WebSocket服务
 *
 * @author Yujiumin
 * @version 2020/8/10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class HomeautoCenterWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterWebSocketApplication.class, args);
    }

}
