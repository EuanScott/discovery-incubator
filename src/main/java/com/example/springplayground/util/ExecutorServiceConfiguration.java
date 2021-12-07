package com.example.springplayground.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
// @Profile("!test")
public class ExecutorServiceConfiguration {

    private final Environment env;

    public ExecutorServiceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean("multiThreadedExecutorService")
    public ExecutorService fixedThreadPool() {
        // TODO: This thread size should be injected in as a qualified dependency item. -> What does this mean?
        return Executors.newFixedThreadPool(Integer.parseInt(env.getProperty("executor.threadPool.maxSize")));
    }

    @Bean("multiThreadedExecutorServiceTimeout")
    public long multiThreadedExecutorServiceTimeout() {
        return Long.parseLong(env.getProperty("executor.timeout.duration"));
    }

    @Bean("multiThreadedExecutorServiceTimeoutType")
    public TimeUnit multiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.valueOf(env.getProperty("executor.timeout.timeUnit"));
    }
}
