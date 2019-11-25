package com.stroganova.movielandapp.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan(basePackages = "com.stroganova.movielandapp.service")
@EnableScheduling
public class ServiceConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService(){
        return Executors.newCachedThreadPool();
    }
}
