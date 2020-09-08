package com.landleaf.homeauto.center.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * WebSocket服务
 *
 * @author Yujiumin
 * @version 2020/8/10
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@ComponentScan("com.landleaf.homeauto")
public class HomeAutoCenterWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeAutoCenterWebSocketApplication.class, args);
    }

}
