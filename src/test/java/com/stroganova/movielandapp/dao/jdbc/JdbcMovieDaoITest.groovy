package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Movie
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

import java.time.LocalDate

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/rootContextTest.xml")
class JdbcMovieDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate
    @Autowired
    MovieDao movieDao

    @Test
    void testGetAll() {

        Map<String, ?>[] movieBatchValues = [
                [id          : 1L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1995-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.15D],
                [id          : 2L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.15D]]
        Map<String, ?>[] posterBatchValues = [
                [id          : 1L,
                 movie_id    : 1L,
                 picture_path: "https://picture_path.png"],
                [id          : 2L,
                 movie_id    : 2L,
                 picture_path: "https://picture_path2.png"]]

        def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
                " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"

        def posterInsertSql = "INSERT INTO movieland.poster (id, movie_id, picture_path)" +
                " VALUES (:id, :movie_id, :picture_path)"

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedParameterJdbcTemplate.batchUpdate(posterInsertSql, posterBatchValues)

        def expectedMovies = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 8.99D,
                        price: 150.15D,
                        picturePath: "https://picture_path.png"),
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.15D,
                        picturePath: "https://picture_path2.png")]


        def actualMovies = movieDao.getAll()

        assert expectedMovies == actualMovies
    }
}
