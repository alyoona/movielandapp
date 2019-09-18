package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.ReviewDao
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
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

class JdbcReviewDaoITest {
    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    ReviewDao reviewDao

    def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
            " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"
    def usersInsertSql = "INSERT INTO movieland.users (id, email, password, first_name, last_name) VALUES (:id, :email, :password, :first_name, :last_name);"
    def reviewInsertSql = "INSERT INTO movieland.review (id, user_id, movie_id, description) VALUES (:id, :user_id, :movie_id, :description);"

    @Before
    void clear() {
        def rolesDeleterSql = "DELETE FROM movieland.roles;"
        def userRolesDeleterSql = "DELETE FROM movieland.user_roles;"
        def usersDeleteSql = "DELETE FROM movieland.users;"
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def reviewDeleteSql = "DELETE FROM movieland.review;"

        namedJdbcTemplate.update(userRolesDeleterSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(rolesDeleterSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(reviewDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(usersDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGetAllByMovieId() {
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 1L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        //users
        namedJdbcTemplate.update(usersInsertSql, [id        : 1000L,
                                                  email     : "testUser@example.com",
                                                  password  : "jfhkjsdfhksfhksh",
                                                  first_name: "Big",
                                                  last_name : "Ben"])
        //reviews
        Map<String, ?>[] reviewBatchValues = [[id: 1L, user_id: 1000L, movie_id: 1L, description: "Excellent!!!"],
                                              [id: 2L, user_id: 1000L, movie_id: 1L, description: "Great!!!"]]
        namedJdbcTemplate.batchUpdate(reviewInsertSql, reviewBatchValues)
        def user = new User(id: 1000L, nickname: "Big Ben")
        def reviews = [new Review(id: 1L, text: "Excellent!!!", user: user), new Review(id: 2L, text: "Great!!!", user: user)]

        def actualReviews = reviewDao.getAll(new Movie(id: 1L))
        assert reviews == actualReviews
    }

    @Test
    void testAdd() {
        def rolesInsertSql = "INSERT INTO movieland.roles (id, name) VALUES (:id, :name);"
        def userRolesInsertSql = "INSERT INTO movieland.user_roles (id, user_id, role_id) VALUES (:id, :user_id, :role_id);"
        namedJdbcTemplate.update(usersInsertSql, [id        : 22L,
                                                  email     : "testUser@example.com",
                                                  password  : "jfhkjsdfhksfhksh",
                                                  first_name: "Big",
                                                  last_name : "Ben"])
        namedJdbcTemplate.update(rolesInsertSql, [id: 5, name: "USER_ROLE"])
        namedJdbcTemplate.update(userRolesInsertSql, [id: 77, user_id: 22, role_id: 5])
        namedJdbcTemplate.update(movieInsertSql, [id          : 27L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        reviewDao.add(new Review(user: new User(id: 22L), movie: new Movie(id: 27L), text: "Great!!!"))
        List<Review> list = reviewDao.getAll(new Movie(id: 27L))
        def review = list.get(0)
        assert review.user.id == 22L
        assert review.user.nickname == "Big Ben"
        assert review.text == "Great!!!"
    }
}
