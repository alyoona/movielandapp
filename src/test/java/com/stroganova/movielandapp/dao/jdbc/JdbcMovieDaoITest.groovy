package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
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
@ContextConfiguration(locations = "classpath:spring/rootContextTest.xml")

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
    def countryInsertSql = "INSERT INTO movieland.country (id, name) VALUES (:id, :name);"
    def movieCountryInsertSql = "INSERT INTO movieland.movie_country (id, movie_id, country_id) VALUES (:id, :movie_id, :country_id);"
    def usersInsertSql = "INSERT INTO movieland.users (id, email, password, first_name, last_name) VALUES (:id, :email, :password, :first_name, :last_name);"
    def reviewInsertSql = "INSERT INTO movieland.review (id, user_id, movie_id, description) VALUES (:id, :user_id, :movie_id, :description);"

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
    void testGetById(){
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
        //countries
        Map<String, ?>[] countryBatchValues = [[id: 10L, name: "countryFirst"],
                                             [id: 20L, name: "countrySecond"]]
        Map<String, ?>[] movieCountryBatchValues = [[id: 1L, movie_id: 1L, country_id: 10L],
                                                  [id: 2L, movie_id: 1L, country_id: 20L]]
        namedJdbcTemplate.batchUpdate(countryInsertSql, countryBatchValues)
        namedJdbcTemplate.batchUpdate(movieCountryInsertSql, movieCountryBatchValues)

        //genres
        Map<String, ?>[] genreBatchValues = [[id: 100L, name: "genreFirst"],
                                             [id: 200L, name: "genreSecond"]]
        Map<String, ?>[] movieGenreBatchValues = [[id: 1L, movie_id: 1L, genre_id: 100L],
                                                  [id: 2L, movie_id: 1L, genre_id: 200L]]
        namedJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

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

        def countries = [new Country(id: 10L, name: "countryFirst"), new Country(id: 20L, name: "countrySecond") ]
        def genres = [new Genre(100L, "genreFirst"), new Genre(200L, "genreSecond")]
        def user = new User(id: 1000L, nickname: "Big Ben")
        def reviews = [new Review(id:1L, text: "Excellent!!!", user: user), new Review(id:2L, text: "Great!!!", user: user)]

        def expectedMovie = new Movie(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1995, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description : "empty",
                countries: countries,
                genres: genres,
                reviews: reviews)
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
                        picturePath: "https://picture_path.png",
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.15D,
                        picturePath: "https://picture_path2.png",
                        description : "empty")]


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
                        price: 150.16D,
                        description : "empty"),
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D,
                        description : "empty")]

        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(new RequestParameter(priceDescSortDirection, null))

        def expectedMoviesPriceAsc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D,
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.16D,
                        description : "empty")]
        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(new RequestParameter(priceAscSortDirection, null))

        def expectedMoviesRatingDesc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 9D,
                        price: 150.15D,
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.16D,
                        description : "empty")]
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
                        picturePath: "https://picture_path.png",
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 8.99D,
                        price: 150.15D,
                        picturePath: "https://picture_path2.png",
                        description : "empty")]


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
                        price: 500D,
                        description : "empty"),
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D,
                        description : "empty")]



        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        assert expectedMoviesPriceDesc == movieDao.getAll(1L, new RequestParameter(priceDescSortDirection, null))

        def expectedMoviesPriceAsc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D,
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 10D,
                        price: 500D,
                        description : "empty")]
        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesPriceAsc == movieDao.getAll(1L, new RequestParameter(priceAscSortDirection, null))

        def expectedMoviesRatingDesc = [
                new Movie(id: 1L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1995, 1, 1),
                        rating: 20D,
                        price: 300D,
                        description : "empty"),
                new Movie(id: 2L,
                        nameRussian: "NameRussian",
                        nameNative: "NameNative",
                        yearOfRelease: LocalDate.of(1994, 1, 1),
                        rating: 10D,
                        price: 500D,
                        description : "empty")]
        def ratingDescSortDirection = new SortDirection("price", SortOrder.ASC)
        assert expectedMoviesRatingDesc == movieDao.getAll(1L, new RequestParameter(ratingDescSortDirection, null))
    }


}
