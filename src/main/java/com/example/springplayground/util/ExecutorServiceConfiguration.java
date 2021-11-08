package com.example.springplayground.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorServiceConfiguration {

    @Bean("multiThreadedExecutorService")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(7);
    }

    @Bean("singleThreadedExecutorService")
    public ExecutorService singleThreadedExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean("multiThreadedExecutorServiceTimeout")
    public long multiThreadedExecutorServiceTimeout() {
        return 10000;
    }

    @Bean("multiThreadedExecutorServiceTimeoutType")
    public TimeUnit multiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.MILLISECONDS;
    }

}
