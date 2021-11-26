package com.example.springplayground.setup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("test")
public class TestExecutorServiceConfiguration {

    @Bean("multiThreadedExecutorService")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean("multiThreadedExecutorServiceTimeout")
    public long multiThreadedExecutorServiceTimeout() {
        return 5000;
    }

    @Bean("multiThreadedExecutorServiceTimeoutType")
    public TimeUnit multiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.MILLISECONDS;
    }
}
