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
        def usersDeleteSql = "DELETE FROM movieland.users;"
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def reviewDeleteSql = "DELETE FROM movieland.review;"

        namedJdbcTemplate.update(reviewDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(usersDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGetAllByMovieId(){
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 1L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        //users
        namedJdbcTemplate.update(usersInsertSql, [id: 1000L,
                                                  email: "testUser@example.com",
                                                  password: "jfhkjsdfhksfhksh",
                                                  first_name: "Big",
                                                  last_name: "Ben"])
        //reviews
        Map<String, ?>[] reviewBatchValues = [[id: 1L, user_id: 1000L, movie_id: 1L, description: "Excellent!!!"],
                                              [id: 2L, user_id: 1000L, movie_id: 1L, description: "Great!!!"]]
        namedJdbcTemplate.batchUpdate(reviewInsertSql, reviewBatchValues)
        def user = new User(id: 1000L, nickname: "Big Ben")
        def reviews = [new Review(id:1L, text: "Excellent!!!", user: user), new Review(id:2L, text: "Great!!!", user: user)]

        def actualReviews = reviewDao.getAll(new Movie(id: 1L))
        assert reviews == actualReviews
    }
}
