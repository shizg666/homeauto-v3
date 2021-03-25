package com.landleaf.homeauto.center.device;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.landleaf.homeauto.center.device.remote")
@EnableSwagger2
@EnableScheduling
@EnableConfigurationProperties
@ComponentScan("com.landleaf.homeauto.*")
@MapperScan("com/landleaf/homeauto/**/mapper")
@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class HomeautoCenterDeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterDeviceApplication.class, args);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Web Interface")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.device.controller.web"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("户式化智能平台 设备中心")
                .description("提供户式化智能平台对外api服务")
                .version("0.1.1")
                .build();
    }

    /*------------------------------------内部服务---------------------------------------*/
    @Bean
    public Docket createRestRemoteApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("remote")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.device.controller.remote"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInRemotefo() {
        return new ApiInfoBuilder()
                .title("户式化智能平台 设备中心")
                .description("提供户式化智能平台内部api服务")
                .version("0.1.1")
                .build();
    }

    /*------------------------------------户式化APP接口配置---------------------------------------*/

    @Bean
    public Docket createRestSmartAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("smart")
                .apiInfo(apiSmartAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.device.controller.app.smart"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiSmartAppInfo() {
        return new ApiInfoBuilder()
                .title("户式化智能平台-户式化APP")
                .description("提供户式化智能平台对外api服务-户式化APP")
                .version("0.1.1")
                .build();
    }

    /*------------------------------------自由方舟APP接口配置---------------------------------------*/

    @Bean
    public Docket createRestNonSmartAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("non-smart")
                .apiInfo(apiNonSmartAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.homeauto.center.device.controller.app.nonsmart"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiNonSmartAppInfo() {
        return new ApiInfoBuilder()
                .title("户式化智能平台-自由方舟APP")
                .description("提供户式化智能平台对外api服务-自由方舟APP")
                .version("0.1.1")
                .build();
    }

}
