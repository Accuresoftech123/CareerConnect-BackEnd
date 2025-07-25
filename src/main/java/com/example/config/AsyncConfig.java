package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync  // Enables @Async processing
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);     // Minimum number of threads
        executor.setMaxPoolSize(10);     // Max threads if demand increases
        executor.setQueueCapacity(100);  // Number of tasks that can be queued
        executor.setThreadNamePrefix("Cloudinary-");
        executor.initialize();
        return executor;
    }
}