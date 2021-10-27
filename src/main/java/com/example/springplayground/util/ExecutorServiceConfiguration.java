package com.example.springplayground.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfiguration {

    @Bean("multiThreaded")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(7);
    }

    @Bean("singleThreaded")
    public ExecutorService singleThreadedExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
