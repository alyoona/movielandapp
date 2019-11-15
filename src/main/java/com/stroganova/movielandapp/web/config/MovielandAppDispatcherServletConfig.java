package com.stroganova.movielandapp.web.config;

import com.stroganova.movielandapp.config.root.ServiceConfig;
import com.stroganova.movielandapp.service.SecurityService;
import com.stroganova.movielandapp.web.handler.RequestParameterArgumentResolver;
import com.stroganova.movielandapp.web.interceptor.RequestLoggingHandlerInterceptor;
import com.stroganova.movielandapp.web.interceptor.SecurityHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.stroganova.movielandapp.web")
@Import({ServiceConfig.class})
public class MovielandAppDispatcherServletConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityService securityService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestParameterArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingHandlerInterceptor());
        registry.addInterceptor(new SecurityHandlerInterceptor(securityService));
    }

}
