package com.stroganova.movielandapp.web.config;

import com.stroganova.movielandapp.service.SecurityService;
import com.stroganova.movielandapp.web.handler.RequestParameterArgumentResolver;
import com.stroganova.movielandapp.web.interceptor.RequestLoggingHandlerInterceptor;
import com.stroganova.movielandapp.web.interceptor.SecurityHandlerInterceptor;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MovielandAppDispatcherServletConfig implements WebMvcConfigurer {

    private final SecurityService securityService;

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
