package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.entity.UserCredentials;

public interface UserService {

    User get(UserCredentials userCredentials);
}
