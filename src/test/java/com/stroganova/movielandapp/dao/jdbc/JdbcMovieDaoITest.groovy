package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
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
    JdbcMovieDao movieDao

    def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
            " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"
    def posterInsertSql = "INSERT INTO movieland.poster (id, movie_id, picture_path)" +
            " VALUES (:id, :movie_id, :picture_path)"

    @Before
    void clear() {
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def posterDeleteSql = "DELETE FROM movieland.poster;"
        namedParameterJdbcTemplate.update(movieDeleteSql,EmptySqlParameterSource.INSTANCE)
        namedParameterJdbcTemplate.update(posterDeleteSql,EmptySqlParameterSource.INSTANCE)
    }

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
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.15D,
                        picturePath: "https://picture_path2.png")]


        def actualMovies = movieDao.getAll()

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetThreeRandomMoviesFromLessThanThree() {
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

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

        assert movieDao.getAll().size() == 2
        assert movieDao.getThreeRandomMovies().size() == 2
    }

    @Test
    void testGetThreeRandomMoviesFromThree() {
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
                 price       : 150.15D],
                [id          : 3L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.15D]]

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

        assert movieDao.getAll().size() == 3
        assert movieDao.getThreeRandomMovies().size() == 3
    }

    @Test
    void testGetThreeRandomMoviesFromMoreThanThree() {
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
                 price       : 150.15D],
                [id          : 3L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.15D],
                [id          : 4L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.15D]]

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

        assert movieDao.getAll().size() == 4
        assert movieDao.getThreeRandomMovies().size() == 3
    }
}
