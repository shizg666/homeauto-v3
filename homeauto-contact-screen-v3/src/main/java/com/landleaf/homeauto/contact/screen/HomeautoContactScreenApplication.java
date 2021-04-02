package com.landleaf.homeauto.contact.screen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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

/**
 * @author wenyilu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableScheduling
@ComponentScan("com.landleaf.homeauto.*")
public class HomeautoContactScreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoContactScreenApplication.class, args);
    }


    @Bean
    public Docket createRestNonSmartCustomerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("contact-screen Interface").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.contact.screen.controller.outer")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("大屏请求云端 APIs").description("提供户式化智能平台对外api服务").version("0.1.1").build();
    }
}
