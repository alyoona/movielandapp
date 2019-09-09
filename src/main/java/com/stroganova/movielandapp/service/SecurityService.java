package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Token;
import com.stroganova.movielandapp.entity.User;

import java.util.Optional;

public interface SecurityService {

    void logout(String uuid);

    Optional<Token> getAuthorization(String uuid);

    Token login(User user);
}
