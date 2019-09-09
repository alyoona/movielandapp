package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.entity.Token;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.exception.NotAuthenticatedException;
import com.stroganova.movielandapp.service.SecurityService;
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

    private volatile Map<String, Token> tokenCache = new ConcurrentHashMap<>();
    private Map<String, Boolean> tokenInvalidIdentifierMap = new ConcurrentHashMap<>();
    private final UserService userService;
    @Value("${securityService.tokenLifeTime}")
    private long tokenLifeTime;

    @Override
    public Token login(User user) {
        Optional<User> userOptional = Optional.ofNullable(userService.get(user));
        if (!userOptional.isPresent()) {
            throw new NotAuthenticatedException("Wrong combination of login or password");
        }
        log.info("Successful signing up for user {}", user.getEmail());
        return getToken(userOptional.get());
    }


    @Override
    public void logout(String uuid) {
        if (getCachedToken(uuid).isPresent()) {
            tokenCache.remove(uuid);
        }
    }

    @Override
    public Optional<Token> getAuthorization(String uuid) {
        return getCachedToken(uuid);
    }

    private Token getToken(User user) {
        String uuid = UUID.randomUUID().toString();
        Token token = new Token(uuid, user, LocalDateTime.now().plusHours(tokenLifeTime));
        tokenCache.put(uuid, token);
        return token;
    }

    private Optional<Token> getCachedToken(String uuid) {
        Optional<Token> tokenOptional = Optional.ofNullable(tokenCache.get(uuid));
        if (tokenOptional.isPresent()) {
            if (tokenOptional.get().getExpirationDate().isBefore(LocalDateTime.now())) {
                tokenInvalidIdentifierMap.putIfAbsent(uuid, true);
                return Optional.empty();
            } else {
                return tokenOptional;
            }
        }
        return tokenOptional;
    }

    @Scheduled(fixedRateString = "${securityService.tokenCacheClearRate}")
    private void clearTokenCache() {
        if (!tokenInvalidIdentifierMap.isEmpty()) {
            Map<String, Token> tokenCacheCopy = new HashMap<>(tokenCache);
            for (String uuid : tokenInvalidIdentifierMap.keySet()) {
                tokenCacheCopy.remove(uuid);
            }
            tokenInvalidIdentifierMap.clear();
            tokenCache = new ConcurrentHashMap<>(tokenCacheCopy);
        }
    }


}
