package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.entity.UserCredentials;

public interface UserDao {

    User get(UserCredentials userCredentials);
}
