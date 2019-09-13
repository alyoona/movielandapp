package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.UserDao
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.entity.UserCredentials
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class DefaultUserServiceTest {

    @Test
    void testGet() {

        def user = new User(id: 22L, email: "testUser@example.com", nickname: "Big Ben")

        def userDao = mock(UserDao.class)
        when(userDao.get(new UserCredentials(email: "testUser@example.com", password: "paco"))).thenReturn(user)

        def userService = new DefaultUserService(userDao)

        def actualUser = userService.get(new UserCredentials(email: "testUser@example.com", password: "paco"))

        assert user == actualUser

    }
}
