package com.landleaf.homeauto.center.id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author wenyilu
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableSwagger2
@EnableScheduling
@ComponentScan("com.landleaf.homeauto.*")
public class HomeautoCenterIdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterIdApplication.class, args);
    }

}
