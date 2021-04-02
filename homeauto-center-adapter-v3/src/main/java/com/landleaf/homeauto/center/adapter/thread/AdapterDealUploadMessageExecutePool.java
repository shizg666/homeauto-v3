package com.landleaf.homeauto.center.adapter.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wenyilu
 */
@Configuration
public class AdapterDealUploadMessageExecutePool {
	 /**
     * 云端下发命令到大屏 多线程处理
     */
    @Bean
    public Executor adapterDealUploadMessageExecute() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(4);
        executor.setKeepAliveSeconds(100);
        executor.setThreadNamePrefix("AdapterDealUploadMessageExecutePool-thread");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}
