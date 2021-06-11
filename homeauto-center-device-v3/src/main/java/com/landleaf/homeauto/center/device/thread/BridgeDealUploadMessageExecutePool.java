package com.landleaf.homeauto.center.device.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhanghongbin
 */
@Configuration
public class BridgeDealUploadMessageExecutePool {
	 /**
     * App下发命令到Bridge通信模块，转给云端下发命令到大屏 多线程处理
     */
    @Bean
    public Executor bridgeDealUploadMessageExecute() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(100);
        executor.setThreadNamePrefix("BridgeDealUploadMessageExecutePool-thread");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}
