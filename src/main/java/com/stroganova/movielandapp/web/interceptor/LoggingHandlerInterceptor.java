package com.stroganova.movielandapp.web.interceptor;

import com.stroganova.movielandapp.entity.Session;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LoggingHandlerInterceptor implements HandlerInterceptor {


    @Autowired
    private SecurityService securityService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String REQUEST_ID_KEY = "requestId";
        String EMAIL_KEY = "email";

        log.info("Before preHandle start");
        if (MDC.get(REQUEST_ID_KEY) != null) {
            MDC.remove(REQUEST_ID_KEY);
        }
        if (MDC.get(EMAIL_KEY) != null) {
            MDC.remove(EMAIL_KEY);
        }

        log.info("preHandle start");

        String requestIdValue = UUID.randomUUID().toString();
        log.info("requestId put into MDC: {}", requestIdValue);
        MDC.put(REQUEST_ID_KEY, requestIdValue);

        Optional<Session> authorizationToken = securityService.getAuthorization(request.getHeader("Uuid"));
        if (authorizationToken.isPresent()) {

            User user = authorizationToken.get().getUser();
            String emailValue = user.getEmail();
            log.info("user email put into MDC: {}", emailValue);

            MDC.put(EMAIL_KEY, emailValue);
        }

        log.info("preHandle end");


        return true;
    }

}
