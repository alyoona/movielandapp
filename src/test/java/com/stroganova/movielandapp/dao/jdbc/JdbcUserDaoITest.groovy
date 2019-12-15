package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.config.TestJdbcDaoConfig
import com.stroganova.movielandapp.dao.UserDao
import com.stroganova.movielandapp.entity.Role
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.security.entity.UserCredentials
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestJdbcDaoConfig.class)
class JdbcUserDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    UserDao userDao

    def usersInsertSql = "INSERT INTO movieland.users (id, email, password, first_name, last_name) " +
            "VALUES (:id, :email, :password, :first_name, :last_name);"
    def rolesInsertSql = "INSERT INTO movieland.roles (id, name) VALUES (:id, :name);"
    def userRolesInsertSql = "INSERT INTO movieland.user_roles (id, user_id, role_id) VALUES (:id, :user_id, :role_id);"

    @Before
    void clear() {
        def reviewDeleteSql = "DELETE FROM movieland.review;"
        namedJdbcTemplate.update(reviewDeleteSql, EmptySqlParameterSource.INSTANCE)
        def usersDeleteSql = "DELETE FROM movieland.users;"
        namedJdbcTemplate.update(usersDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGet() {
        namedJdbcTemplate.update(usersInsertSql, [id        : 22L,
                                                  email     : "testUser@example.com",
                                                  password  : "jfhkjsdfhksfhksh",
                                                  first_name: "Big",
                                                  last_name : "Ben"])
        namedJdbcTemplate.update(rolesInsertSql, [id: 5, name: "USER"])
        namedJdbcTemplate.update(userRolesInsertSql, [id: 77, user_id: 22, role_id: 5])

        def user = new User.UserBuilder(id: 22L, nickname: "Big Ben", email: "testUser@example.com", role: Role.USER)
                .build()
        def actualUser = userDao.get(new UserCredentials("testUser@example.com", "jfhkjsdfhksfhksh"))
        assert user == actualUser
    }
}


