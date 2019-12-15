package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.security.entity.UserCredentials;

public interface UserDao {

    User get(UserCredentials userCredentials);
}
