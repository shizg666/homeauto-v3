package com.landleaf.homeauto.center.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.landleaf.homeauto")
public class HomeautoCenterWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterWeatherApplication.class, args);
    }

}
