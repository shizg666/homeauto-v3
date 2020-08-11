package com.landleaf.homeauto.center.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com.landleaf.homeauto.**.mapper")
public class HomeautoCenterOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterOauthApplication.class, args);
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Web Interface").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.oauth.web.controller.web")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Web端 APIs").description("提供户式化智能平台对外api服务").version("0.1.1").build();
    }
    @Bean
    public Docket createRestCustomerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("APP Interface").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.oauth.web.controller.app")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiCustomerInfo() {
        return new ApiInfoBuilder().title("APP端客户 APIs").description("提供户式化智能平台对外app-api服务").version("0.1.1").build();
    }
    @Bean
    public Docket createRestNonSmartCustomerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("自由方舟APP Interface").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.oauth.web.controller.app")).paths(PathSelectors.ant("/auth/customer/app/non-smart/**")).build();
    }

    private ApiInfo apiNonSmartCustomerInfo() {
        return new ApiInfoBuilder().title("自由方舟APP端客户 APIs").description("提供户式化智能平台对外自由方舟app-api服务").version("0.1.1").build();
    }
}
