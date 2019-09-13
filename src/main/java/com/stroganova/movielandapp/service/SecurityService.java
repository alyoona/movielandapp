package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Session;
import com.stroganova.movielandapp.entity.UserCredentials;

import java.util.Optional;

public interface SecurityService {

    void logout(String uuid);

    Optional<Session> getAuthorization(String uuid);

    Session login(UserCredentials user);
}
