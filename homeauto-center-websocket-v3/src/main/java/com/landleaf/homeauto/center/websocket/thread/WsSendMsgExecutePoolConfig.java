package com.landleaf.homeauto.center.websocket.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wenyilu
 */
@Configuration
public class WsSendMsgExecutePoolConfig {

    /**
     * 记录日志 多线程处理
     */
    @Bean
    public Executor wsSendMsgExecutePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(500);
        executor.setQueueCapacity(2000);
        executor.setKeepAliveSeconds(100);
        executor.setThreadNamePrefix("WsSendMsgExecutePool");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}