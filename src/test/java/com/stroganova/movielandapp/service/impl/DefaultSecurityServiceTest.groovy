package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.exception.NotAuthenticatedException
import com.stroganova.movielandapp.service.UserService
import org.junit.Test
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class DefaultSecurityServiceTest {

    @Test
    void testLogin() {

        def userService = mock(UserService.class)
        def user = new User(id: 1L, email: "test@example.com", nickname: "Big Ben")
        when(userService.get(new User(email: "test@example.com", password: "paco"))).thenReturn(user)

        def securityService = new DefaultSecurityService(userService)
        Field field = DefaultSecurityService.getDeclaredField("tokenLifeTime")
        field.setAccessible(true)
        ReflectionUtils.setField(field, securityService, 2L)
        field.setAccessible(false)

        def token = securityService.login(new User(email: "test@example.com", password: "paco"))

        def uuid = token.getUuid()

        def cachedToken = securityService.getAuthorization(uuid)

        assert token == cachedToken.get()
    }

    @Test
    void testLogout() {

        def userService = mock(UserService.class)
        def user = new User(id: 1L, email: "test@example.com", nickname: "Big Ben")
        when(userService.get(new User(email: "test@example.com", password: "paco"))).thenReturn(user)

        def securityService = new DefaultSecurityService(userService)
        Field field = DefaultSecurityService.getDeclaredField("tokenLifeTime")
        field.setAccessible(true)
        ReflectionUtils.setField(field, securityService, 2L)
        field.setAccessible(false)

        def token = securityService.login(new User(email: "test@example.com", password: "paco"))

        def uuid = token.getUuid()

        securityService.logout(uuid)

        def cachedToken = securityService.getAuthorization(uuid)

        assert !cachedToken.isPresent()

    }

    @Test
    void testNotAuthenticatedException() {
        def userService = mock(UserService.class)
        when(userService.get(new User(email: "test@example.com", password: "paco"))).thenReturn(null)
        def securityService = new DefaultSecurityService(userService)
        def ex = expectThrown(NotAuthenticatedException) {
            securityService.login(new User(email: "test@example.com", password: "paco"))
        }
        assert ex.class == NotAuthenticatedException.class
        assert ex.getMessage() == "Wrong combination of login or password"
    }

    def static expectThrown(Class expectedThrowable = Throwable, Closure closure) {
        try {
            closure()
        } catch (Throwable t) {
            if (!expectedThrowable.isInstance(t)) {
                throw t
            }
            return t
        }
        throw new AssertionError("Expected Throwable $expectedThrowable not thrown")
    }


}
