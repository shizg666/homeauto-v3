package com.landleaf.homeauto.center.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@ComponentScan("com.landleaf.homeauto.*")
public class HomeautoCenterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeautoCenterGatewayApplication.class, args);
    }


    // 测试自定义过滤器
    @Bean
    public RouteLocator contactGatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/test12/**")
                        .filters(f -> f.filter(new RequestTimeFilter())
                              .filter(new StripPrefixGatewayFilterFactory().apply(c->{
                                  c.setParts(1);
                              })))
                        .uri("lb://homeauto-contact-gateway")
                        .order(0)
                        .id("homeauto_contact_gateway1")
                )
                .build();
    }

}
