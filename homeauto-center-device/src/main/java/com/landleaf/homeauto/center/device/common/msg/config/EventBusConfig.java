package com.landleaf.homeauto.center.device.common.msg.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

/**
 * spring中注册 guava 事件总现模型
 */
@Configuration
public class EventBusConfig {

    @Bean
    public EventBus eventBus(){
        return new EventBus();
    }

    @Bean
    public AsyncEventBus asyncEventBus(@Qualifier("msgEventExecutor") Executor executor){
        return new AsyncEventBus(executor);
    }
}
