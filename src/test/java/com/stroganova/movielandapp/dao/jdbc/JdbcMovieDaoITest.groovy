package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
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
    def genreInsertSql = "INSERT INTO movieland.genre (id, name) VALUES (:id, :name);"
    def movieGenreInsertSql = "INSERT INTO movieland.movie_genre (id, movie_id, genre_id) VALUES (:id, :movie_id, :genre_id);"

    @Before
    void clear() {
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def posterDeleteSql = "DELETE FROM movieland.poster;"
        def genreDeleteSql = "DELETE FROM movieland.genre;"
        def movieGenreDeleteSql = "DELETE FROM movieland.movie_genre;"
        namedParameterJdbcTemplate.update(movieGenreDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedParameterJdbcTemplate.update(genreDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedParameterJdbcTemplate.update(posterDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedParameterJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
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
    void testGetAllAndSort() {

        Map<String, ?>[] movieBatchValues = [
                [id          : 1L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1995-01-01",
                 description : "empty",
                 rating      : 9D,
                 price       : 150.15D],
                [id          : 2L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 8.99D,
                 price       : 150.16D]]

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

        def expectedMoviesPriceDesc = [
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.16D),
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D)]

        def priceDescSortDirection = new SortDirection(field: "price", orderValue: SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(priceDescSortDirection)

        def expectedMoviesPriceAsc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.16D)]
        def priceAscSortDirection = new SortDirection(field: "price", orderValue: SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(priceAscSortDirection)

        def expectedMoviesRatingDesc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.16D)]
        def ratingDescSortDirection = new SortDirection(field: "price", orderValue: SortOrder.ASC)
        assert expectedMoviesRatingDesc == movieDao.getAll(ratingDescSortDirection)

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

    @Test
    void testGetAllByGenreId() {
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

        Map<String, ?>[] genreBatchValues = [[id: 1L, name: "genreFirst"]]

        Map<String, ?>[] movieGenreBatchValues = [[id: 10L, movie_id: 1L, genre_id: 1L],
                                                  [id: 20L, movie_id: 2L, genre_id: 1L]]

        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedParameterJdbcTemplate.batchUpdate(posterInsertSql, posterBatchValues)
        namedParameterJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedParameterJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

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


        def actualMovies = movieDao.getAll(1L)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllByGenreIdAndSort() {
        Map<String, ?>[] movieBatchValues = [
                [id          : 1L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1995-01-01",
                 description : "empty",
                 rating      : 20D,
                 price       : 300D],
                [id          : 2L,
                 name_russian: "NameRussian",
                 name_native : "NameNative",
                 year        : "1994-01-01",
                 description : "empty",
                 rating      : 10D,
                 price       : 500D]]
        Map<String, ?>[] genreBatchValues = [[id: 1L, name: "genreFirst"]]

        Map<String, ?>[] movieGenreBatchValues = [[id: 10L, movie_id: 1L, genre_id: 1L],
                                                  [id: 20L, movie_id: 2L, genre_id: 1L]]
        namedParameterJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedParameterJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedParameterJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

        def expectedMoviesPriceDesc = [
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 10D,
                        price: 500D),
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D)]



        def priceDescSortDirection = new SortDirection(field: "price", orderValue: SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(1L, priceDescSortDirection)

        def expectedMoviesPriceAsc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 10D,
                        price: 500D)]
        def priceAscSortDirection = new SortDirection(field: "price", orderValue: SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(1L, priceAscSortDirection)

        def expectedMoviesRatingDesc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 10D,
                        price: 500D)]
        def ratingDescSortDirection = new SortDirection(field: "price", orderValue: SortOrder.ASC)
        assert expectedMoviesRatingDesc == movieDao.getAll(1L, ratingDescSortDirection)
    }


}
