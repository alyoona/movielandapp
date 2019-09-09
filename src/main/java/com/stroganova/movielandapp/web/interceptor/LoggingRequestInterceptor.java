package com.stroganova.movielandapp.web.interceptor;

import com.stroganova.movielandapp.entity.Token;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class LoggingRequestInterceptor implements HandlerInterceptor {

   private final SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle start");

        String requestId = UUID.randomUUID().toString();
        log.info("requestId put into MDC: " + requestId);
        MDC.put("requestId", requestId);

        Optional<Token> authorizationToken = securityService.getAuthorization(request.getHeader("Uuid"));
        if(authorizationToken.isPresent()) {
            User user = authorizationToken.get().getUser();
            String email =  user.getEmail();
            log.info("user email put into MDC: " + email);
            MDC.put("user",email);
        }

        log.info("preHandle end");


        return true;
    }

}
