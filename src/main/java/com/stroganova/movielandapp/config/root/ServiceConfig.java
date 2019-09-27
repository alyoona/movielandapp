package com.stroganova.movielandapp.config.root;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = "com.stroganova.movielandapp.service")
@EnableScheduling
public class ServiceConfig {
}
