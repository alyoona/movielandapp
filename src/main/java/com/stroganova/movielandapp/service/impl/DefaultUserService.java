package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.UserDao;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{

   private final UserDao userDao;

    @Override
    public User get(User user) {
        return userDao.get(user);
    }
}
