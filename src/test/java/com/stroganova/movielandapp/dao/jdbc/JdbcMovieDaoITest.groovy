package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.config.TestJdbcDaoConfig
import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.MovieUpdateDirections
import com.stroganova.movielandapp.request.RequestParameter
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
@ContextConfiguration(classes = TestJdbcDaoConfig.class)

class JdbcMovieDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    MovieDao movieDao

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
        def countryDeleteSql = "DELETE FROM movieland.country;"
        def movieCountryDeleteSql = "DELETE FROM movieland.movie_country;"
        def usersDeleteSql = "DELETE FROM movieland.users;"
        def reviewDeleteSql = "DELETE FROM movieland.review;"

        namedJdbcTemplate.update(movieGenreDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(genreDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(movieCountryDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(countryDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(reviewDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(usersDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(posterDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
    }


    @Test
    void testPartialUpdate() {
        //movie
        long movieId = 26L
        namedJdbcTemplate.update(movieInsertSql, [id          : movieId,
                                                  name_russian: "",
                                                  name_native : "",
                                                  year        : "1970-01-01",
                                                  description : "",
                                                  rating      : 0D,
                                                  price       : 0D])

        def movie = new Movie(
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                description: "MovieDescription!!!")

        Map<MovieFieldUpdate, Object> mapUpdates = new HashMap<>()
        for (MovieFieldUpdate fieldUpdate : MovieFieldUpdate.values()) {
            mapUpdates.put(fieldUpdate, fieldUpdate.getValue(movie))
        }

        def updates = new MovieUpdateDirections(mapUpdates)
        movieDao.partialUpdate(movieId, updates.getMovieUpdates())

        def expectedMovie = new Movie(id: movieId,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                description: "MovieDescription!!!")


        def actualMovie = movieDao.getById(movieId)

        assert expectedMovie == actualMovie

    }


    @Test
    void testAdd() {
        def movie = new Movie(nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                description: "empty",
                rating: 8.99D,
                price: 150.15D)

        movieDao.add(movie)
        def addedMovie = movieDao.getById(movieDao.getNewestMovieId())
        assert movie.getNameRussian() == addedMovie.getNameRussian()
        assert movie.getNameNative() == addedMovie.getNameNative()
        assert movie.getYearOfRelease() == addedMovie.getYearOfRelease()
        assert movie.getPrice() == addedMovie.getPrice()
        assert movie.getRating() == addedMovie.getRating()
        assert movie.getDescription() == addedMovie.getDescription()

    }

    @Test
    void testGetById() {
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 1L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])

        namedJdbcTemplate.update(posterInsertSql, [id          : 1L,
                                                   movie_id    : 1L,
                                                   picture_path: "https://picture_path.png"])

        def expectedMovie = new Movie(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1995, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty")
        def actualMovie = movieDao.getById(1L)

        assert expectedMovie == actualMovie

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedJdbcTemplate.batchUpdate(posterInsertSql, posterBatchValues)

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

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

        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(new RequestParameter(priceDescSortDirection, null))

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
        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(new RequestParameter(priceAscSortDirection, null))

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
        def ratingDescSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesRatingDesc == movieDao.getAll(new RequestParameter(ratingDescSortDirection, null))

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)

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

        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedJdbcTemplate.batchUpdate(posterInsertSql, posterBatchValues)
        namedJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

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
        namedJdbcTemplate.batchUpdate(movieInsertSql, movieBatchValues)
        namedJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

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



        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(1L, new RequestParameter(priceDescSortDirection, null))

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
        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(1L, new RequestParameter(priceAscSortDirection, null))

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
        def ratingDescSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesRatingDesc == movieDao.getAll(1L, new RequestParameter(ratingDescSortDirection, null))
    }


}
