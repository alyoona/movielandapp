package com.stroganova.movielandapp.config.root;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@Import({JdbcDaoConfig.class, ServiceConfig.class})
public class RootConfig {
}
