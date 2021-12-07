package com.example.springplayground.setup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
// @Profile("test")
// Cannot register bean definition [...] for bean 'multiThreadedExecutorService': There is already bound.
public class TestExecutorServiceConfiguration {

    @Bean("testMultiThreadedExecutorService")
    public ExecutorService testFixedThreadPool() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean("testMultiThreadedExecutorServiceTimeout")
    public long testMultiThreadedExecutorServiceTimeout() {
        return 5000;
    }

    @Bean("testMultiThreadedExecutorServiceTimeoutType")
    public TimeUnit testMultiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.MILLISECONDS;
    }
}
