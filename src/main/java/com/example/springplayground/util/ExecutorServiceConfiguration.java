package com.example.springplayground.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorServiceConfiguration {

    // TODO: When testing env is null!
    private final Environment env;

    public ExecutorServiceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean("multiThreadedExecutorService")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(7); //Integer.parseInt(env.getProperty("executor.threadPool.maxSize")));
    }

    // @Bean("singleThreadedExecutorService")
    // public ExecutorService singleThreadedExecutor() {
    //     return Executors.newSingleThreadExecutor();
    // }

    @Bean("multiThreadedExecutorServiceTimeout")
    public long multiThreadedExecutorServiceTimeout() {
        return 10000; //Long.parseLong(env.getProperty("executor.timeout.duration"));
    }

    @Bean("multiThreadedExecutorServiceTimeoutType")
    public TimeUnit multiThreadedExecutorServiceTimeoutType() {
        return TimeUnit.MILLISECONDS; //TimeUnit.valueOf(env.getProperty("executor.timeout.timeUnit"));
    }

}
