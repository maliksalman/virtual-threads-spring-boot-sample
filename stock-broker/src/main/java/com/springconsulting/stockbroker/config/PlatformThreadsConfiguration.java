package com.springconsulting.stockbroker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("!virtual")
@Configuration
@EnableScheduling
public class PlatformThreadsConfiguration {

    @Profile("!fixed")
    @Bean(destroyMethod = "shutdown")
    public ExecutorService cachedExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Profile("fixed")
    @Bean(destroyMethod = "shutdown")
    public ExecutorService fixedExecutorService() {
        return Executors.newFixedThreadPool(1024);
    }
}
