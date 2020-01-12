package com.esb.bbf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * taskExecutor
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(3);
        pool.setMaxPoolSize(5);
        pool.setKeepAliveSeconds(100);
        pool.setQueueCapacity(100);
        pool.setThreadNamePrefix("thread-pool");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务  3
        /*
        (1)ThreadPoolExecutor.AbortPolicy策略，是默认的策略,处理程序遭到拒绝将抛出运行时
        RejectedExecutionException。
        (2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃
        (3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
        (4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的
                                       任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）.
        */
        //pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.initialize();
        return pool;
    }
}
