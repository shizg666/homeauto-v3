package com.landleaf.homeauto.center.device;

import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoTokenProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
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
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Web Interface").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.device.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("户式化智能平台 设备中心").description("提供户式化智能平台对外api服务").version("0.1.1").build();
    }
}
