package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.UserDao
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.entity.UserCredentials
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/rootContextTest.xml")

class JdbcUserDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    UserDao userDao

    def usersInsertSql = "INSERT INTO movieland.users (id, email, password, first_name, last_name) " +
            "VALUES (:id, :email, :password, :first_name, :last_name);"
    @Before
    void clear() {
        def reviewDeleteSql = "DELETE FROM movieland.review;"
        namedJdbcTemplate.update(reviewDeleteSql, EmptySqlParameterSource.INSTANCE)
        def usersDeleteSql = "DELETE FROM movieland.users;"
        namedJdbcTemplate.update(usersDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGet(){
        namedJdbcTemplate.update(usersInsertSql, [id: 22L,
                                                  email: "testUser@example.com",
                                                  password: "jfhkjsdfhksfhksh",
                                                  first_name: "Big",
                                                  last_name: "Ben"])
        def user = new User(id: 22L, email: "testUser@example.com", nickname: "Big Ben")
        def actualUser = userDao.get(new UserCredentials(email: "testUser@example.com", password: "jfhkjsdfhksfhksh" ))
        assert user == actualUser
    }
}


