package com.landleaf.homeauto.center.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com/landleaf/homeauto/**/mapper")
@SpringBootApplication
public class HomeautoCenterDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterDataApplication.class, args);
    }

}
