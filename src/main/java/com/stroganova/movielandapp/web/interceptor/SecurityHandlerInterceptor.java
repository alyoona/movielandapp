package com.stroganova.movielandapp.web.interceptor;

import com.stroganova.movielandapp.entity.Session;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Slf4j
public class SecurityHandlerInterceptor implements HandlerInterceptor {

    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("SecurityHandlerInterceptor preHandle start");

        String requestMappingName = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        SecurityMapping securityMapping = SecurityMapping.get(requestMappingName, request.getMethod());

        if (SecurityMapping.ALLOWED.equals(securityMapping)) {
            return true;
        } else {
            Optional<Session> sessionOptional = securityService.getAuthorization(request.getHeader("Token"));
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                User user = session.getUser();
                if (securityMapping.getRoles().contains(user.getRole())) {
                    request.setAttribute("user", user);
                    return true;
                }
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
