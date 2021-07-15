package com.landleaf.homeauto.center.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com/landleaf/homeauto/**/mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.landleaf.homeauto.center.data.remote")
@EnableScheduling
public class HomeautoCenterDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterDataApplication.class, args);
    }

}
