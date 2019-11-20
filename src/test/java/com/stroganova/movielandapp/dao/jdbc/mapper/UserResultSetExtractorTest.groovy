package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Role
import com.stroganova.movielandapp.entity.User
import org.junit.Test

import java.sql.ResultSet
import static org.mockito.Mockito.mock

import static org.mockito.Mockito.when

class UserResultSetExtractorTest {

    @Test
    void testExtractData() {
        def resultSet = mock(ResultSet.class)
        when(resultSet.next()).thenReturn(true)
        when(resultSet.getLong("id")).thenReturn(1L)
        when(resultSet.getString("email")).thenReturn("ronald.reynolds66@example.com")
        when(resultSet.getString("first_name")).thenReturn("FirstName")
        when(resultSet.getString("last_name")).thenReturn("LastName")
        when(resultSet.getString("role_name")).thenReturn("USER")
        def userResultSetExtractor = new UserResultSetExtractor()
        def actualUser = userResultSetExtractor.extractData(resultSet)
        def user = new User.UserBuilder(
                id: 1,
                nickname: "FirstName LastName",
                email: "ronald.reynolds66@example.com",
                role: Role.USER
        ).build()
        assert user == actualUser
    }
}
