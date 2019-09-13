package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.GenreService
import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.request.Currency
import com.stroganova.movielandapp.request.RequestParameter
import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
import com.stroganova.movielandapp.service.CountryService
import com.stroganova.movielandapp.service.CurrencyService
import com.stroganova.movielandapp.service.MovieService
import com.stroganova.movielandapp.service.ReviewService
import org.junit.Before
import org.junit.Test

import java.time.LocalDate

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DefaultMovieServiceTest {

    private MovieService movieService
    private MovieDao movieDao
    private CountryService countryService
    private GenreService genreService
    private ReviewService reviewService
    private CurrencyService currencyService

    @Before
    void before() {
        movieDao = mock(MovieDao.class)
        countryService = mock(CountryService.class)
        genreService = mock(GenreService.class)
        reviewService = mock(ReviewService.class)
        currencyService = mock(CurrencyService.class)
        movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService)
    }

    @Test
    void testGetByIdAndConvert() {

        def movie = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty"
        )
        when(movieDao.getById(1L)).thenReturn(movie)
        when(currencyService.convert(movie.getPrice(), Currency.USD)).thenReturn(Double.valueOf(8.99 / 25))
        def actualMovie = movieService.getById(1L, new RequestParameter(null, Currency.USD))
        assert movie == actualMovie
        assert Double.valueOf(8.99 / 25) == actualMovie.getPrice()
    }

    @Test
    void testGetById() {

        def countries = [new Country(id: 10L, name: "USA"), new Country(id: 20, name: "GB")]
        def genres = [new Genre(100L, "FirstGenre")]
        def reviews = [new Review(id: 1000, text: "Great!", user: new User(id: 50, nickname: "Big Ben"))]
        def movie = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
                countries: countries,
                genres: genres,
                reviews: reviews
        )

        when(movieDao.getById(1L)).thenReturn(movie)
        when(countryService.getAll(movie)).thenReturn(countries)
        when(genreService.getAll(movie)).thenReturn(genres)
        when(reviewService.getAll(movie)).thenReturn(reviews)

        def actualMovie = movieService.getById(1L)

        assert movie == actualMovie
    }

    @Test
    void testGetAll() {

        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getAll()).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll()

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllByGenreId() {

        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getAll(1L)).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll(1L)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetThreeRandomMovies() {
        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getThreeRandomMovies()).thenReturn(expectedMovies)

        def actualMovies = movieService.getThreeRandomMovies()

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllAndSort() {

        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        def expectedMovies = [movieFirst, movieSecond]

        def sortDirection = new SortDirection("test", SortOrder.ASC)
        def requestParameter = new RequestParameter(sortDirection, null)

        when(movieDao.getAll(requestParameter)).thenReturn(expectedMovies)
        def actualMovies = movieService.getAll(requestParameter)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllByGenreIdAndSort() {

        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        def expectedMovies = [movieFirst, movieSecond]

        def sortDirection = new SortDirection("test", SortOrder.ASC)
        def requestParameter = new RequestParameter(sortDirection, null)

        when(movieDao.getAll(1L, requestParameter)).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll(1L, requestParameter)

        assert expectedMovies == actualMovies
    }
}
