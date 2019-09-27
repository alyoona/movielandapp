package com.stroganova.movielandapp.config.web;


import com.stroganova.movielandapp.service.SecurityService;
import com.stroganova.movielandapp.web.handler.RequestParameterArgumentResolver;
import com.stroganova.movielandapp.web.handler.UserRequestAttributeArgumentResolver;
import com.stroganova.movielandapp.web.interceptor.LoggingHandlerInterceptor;
import com.stroganova.movielandapp.web.interceptor.SecurityHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.stroganova.movielandapp.web")
public class MovielandAppDispatcherServletConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityService securityService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestParameterArgumentResolver());
        resolvers.add(new UserRequestAttributeArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingHandlerInterceptor(securityService));
        registry.addInterceptor(new SecurityHandlerInterceptor(securityService));
    }
}
