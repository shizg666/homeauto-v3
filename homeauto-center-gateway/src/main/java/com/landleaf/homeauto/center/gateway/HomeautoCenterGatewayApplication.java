package com.landleaf.homeauto.center.gateway;

import com.landleaf.homeauto.center.gateway.filter.AddTokenFilter;
import com.landleaf.homeauto.center.gateway.filter.ZuulExceptionFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author wenyilu
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@ComponentScan({"com.landleaf.homeauto.*"})
@EnableZuulProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HomeautoCenterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterGatewayApplication.class, args);
    }

    @Bean
    public AddTokenFilter addTokenFilter() {
        return new AddTokenFilter();
    }
    @Bean
    public ZuulExceptionFilter zuulExceptionFilter() {
        return new ZuulExceptionFilter();
    }

}
