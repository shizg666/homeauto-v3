package com.landleaf.homeauto.center.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author wenyilu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@ComponentScan("com.landleaf.homeauto.*")
@EnableZuulProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HomeautoCenterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterGatewayApplication.class, args);
    }


}
