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

        def user = new User.UserBuilder(id: 22L, email: "testUser@example.com", nickname: "Big Ben").build()

        def userDao = mock(UserDao.class)
        when(userDao.get(new UserCredentials("testUser@example.com", "paco"))).thenReturn(user)

        def userService = new DefaultUserService(userDao)

        def actualUser = userService.get(new UserCredentials("testUser@example.com", "paco"))

        assert user == actualUser

    }
}
