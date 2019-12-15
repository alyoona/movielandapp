package com.stroganova.movielandapp.security.service;

import com.stroganova.movielandapp.security.entity.Session;
import com.stroganova.movielandapp.security.entity.UserCredentials;

import java.util.Optional;

public interface SecurityService {

    void logout(String uuid);

    Optional<Session> getAuthorization(String uuid);

    Session login(UserCredentials user);
}
