package com.stroganova.movielandapp.web.interceptor;

import com.stroganova.movielandapp.entity.Role;
import com.stroganova.movielandapp.entity.Session;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.service.SecurityService;
import com.stroganova.movielandapp.web.annotation.Secured;
import com.stroganova.movielandapp.web.handler.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SecurityHandlerInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    public SecurityHandlerInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("SecurityHandlerInterceptor preHandle start");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Secured secured = method.getDeclaredAnnotation(Secured.class);
        if (secured != null) {

            Optional<Session> authorizationToken = securityService.getAuthorization(request.getHeader("Token"));
            if (authorizationToken.isPresent()) {
                User user = authorizationToken.get().getUser();

                UserHolder.setLoggedUser(user);
                mdcPut(user);

                List<Role> roles = user.getRole().getIncludedRights();
                if (roles.contains(secured.role())) {
                    return true;
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private void mdcPut(User user){
            String emailValue = user.getEmail();
            log.info("user email put into MDC: {}", emailValue);
            MDC.put("email", emailValue);
        }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("SecurityHandlerInterceptor afterCompletion start");

        UserHolder.clear();

        MDC.remove("email");

    }
}
