package com.stroganova.movielandapp.security.service.impl;

import com.stroganova.movielandapp.security.entity.Session;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.security.entity.UserCredentials;
import com.stroganova.movielandapp.exception.NotAuthenticatedException;
import com.stroganova.movielandapp.security.service.SecurityService;
import com.stroganova.movielandapp.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultSecurityService implements SecurityService {

    private final UserService userService;
    private Map<String, Session> tokenCache = new ConcurrentHashMap<>();
    @Value("${securityService.tokenLifeTime.minutes:120}")
    private long tokenLifeTime;

    @Override
    public Session login(UserCredentials userCredentials) {
        Optional<User> userOptional = Optional.ofNullable(userService.get(userCredentials));
        if (!userOptional.isPresent()) {
            throw new NotAuthenticatedException("Wrong combination of login or password");
        }
        log.info("Successful signing up for user {}", userCredentials.getEmail());
        return createAndCacheSession(userOptional.get());
    }

    @Override
    public void logout(String token) {
        tokenCache.remove(token);
    }

    @Override
    public Optional<Session> getAuthorization(String token) {
        return getCachedToken(token);
    }

    private Session createAndCacheSession(User user) {
        String token = UUID.randomUUID().toString();
        Session session = new Session(token, user, LocalDateTime.now().plusMinutes(tokenLifeTime));
        tokenCache.put(token, session);
        return session;
    }

    private Optional<Session> getCachedToken(String token) {
        if (token != null) {
            Optional<Session> tokenOptional = Optional.ofNullable(tokenCache.get(token));
            if (tokenOptional.isPresent()) {

                return removeIfTokenExpired(tokenOptional.get()) ? Optional.empty() : tokenOptional;
            }
            return tokenOptional;
        }
        return Optional.empty();
    }

    private boolean removeIfTokenExpired(Session session) {
        if (session.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenCache.remove(session.getUuid());
            return true;
        }
        return false;
    }

    @Scheduled(fixedRateString = "${securityService.tokenCacheClearRate}")
    private void clearTokenCache() {
        for (String token : tokenCache.keySet()) {
            Session session = tokenCache.get(token);
            removeIfTokenExpired(session);
        }
    }


}
