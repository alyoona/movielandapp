package com.stroganova.movielandapp.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class RequestLoggingHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        log.info("preHandle start");

        String requestIdValue = UUID.randomUUID().toString();
        log.info("requestId put into MDC: {}", requestIdValue);
        MDC.put("requestId", requestIdValue);

        log.info("preHandle end");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion start");
        MDC.remove("requestId");
    }
}
