package com.example.springplayground.setup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class TestExecutorServiceConfiguration {

    @Bean("multiThreadedExecutorService")
    @Profile("test")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(7);
    }

    @Bean("singleThreadedExecutorService")
    @Profile("test")
    public ExecutorService singleThreadedExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean("multiThreadedExecutorServiceTimeout")
    @Profile("test")
    public long multiThreadedExecutorServiceTimeout() {
        return 10000;
    }

    @Bean("multiThreadedExecutorServiceTimeoutType")
    @Profile("test")
    public TimeUnit multiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.MILLISECONDS;
    }

}