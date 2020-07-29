package com.landleaf.homeauto.contact.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableScheduling
@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com.landleaf.homeauto.**.mapper")
public class HomeautoContactGatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(HomeautoContactGatewayApplication.class, args);

        String url = applicationContext.getEnvironment().getProperty("spring.datasource.url");
        System.out.println(url);
    }

}
