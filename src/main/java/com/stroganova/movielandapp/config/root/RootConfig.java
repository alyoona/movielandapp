package com.stroganova.movielandapp.config.root;

import org.springframework.context.annotation.*;

@Configuration
@Import({JdbcDaoConfig.class, ServiceConfig.class})
@PropertySource("classpath:application.properties")
public class RootConfig {


}
