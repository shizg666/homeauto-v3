package com.landleaf.homeauto.center.device;

import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoTokenProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableScheduling
@EnableConfigurationProperties
@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com/landleaf/homeauto/**/mapper")
public class HomeautoCenterDeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterDeviceApplication.class, args);
    }

}
