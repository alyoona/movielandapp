package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.request.Currency
import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.MovieUpdateDirections
import com.stroganova.movielandapp.request.RequestParameter
import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
import com.stroganova.movielandapp.service.CountryService
import com.stroganova.movielandapp.service.CurrencyService
import com.stroganova.movielandapp.service.GenreService
import com.stroganova.movielandapp.service.MovieEnrichmentService
import com.stroganova.movielandapp.service.MovieService
import com.stroganova.movielandapp.service.PosterService
import com.stroganova.movielandapp.service.ReviewService
import com.stroganova.movielandapp.service.cache.MovieCache
import org.junit.Before
import org.junit.Test

import java.time.LocalDate

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class DefaultMovieServiceTest {

    private MovieService movieService
    private MovieDao movieDao
    private CountryService countryService
    private GenreService genreService
    private ReviewService reviewService
    private CurrencyService currencyService
    private PosterService posterService
    private MovieCache movieCache
    private MovieEnrichmentService enrichmentService

    @Before
    void before() {
        movieDao = mock(MovieDao.class)
        countryService = mock(CountryService.class)
        genreService = mock(GenreService.class)
        reviewService = mock(ReviewService.class)
        currencyService = mock(CurrencyService.class)
        posterService = mock(PosterService.class)
        movieCache = mock(MovieCache.class)
        enrichmentService = mock(ParallelMovieEnrichmentService.class)

        movieService = new DefaultMovieService(movieDao, countryService, genreService, currencyService, posterService, movieCache, enrichmentService)

    }

    @Test
    void testUpdate() {
        def movie = new Movie.MovieBuilder(id: 25L,
                nameRussian: "NEWNameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(2000, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: [new Country(3, null)],
                genres: [new Genre(3, null)]
        ).build()

        when(movieDao.getById(25L)).thenReturn(movie)
        when(enrichmentService.enrich(movie)).thenReturn(movie)

        assert movie == movieService.update(movie)

        verify(movieDao).update(movie)
        verify(posterService).update(movie.id, movie.getPicturePath())
        verify(countryService).updateLinks(movie.id, movie.countries)
        verify(genreService).updateLinks(movie.id, movie.genres)

    }


    @Test
    void testPartialUpdate() {
        def movie = new Movie.MovieBuilder(nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: [new Country(1, null), new Country(2, null), new Country(3, null)],
                genres: [new Genre(1, null), new Genre(2, null)]
        ).build()
        Map<MovieFieldUpdate, Object> map = new HashMap<>()
        for (MovieFieldUpdate fieldUpdate : MovieFieldUpdate.values()) {
            map.put(fieldUpdate, fieldUpdate.getValue(movie))
        }

        long movieId = 26L
        def updates = new MovieUpdateDirections(map)

        def updatedMovie = new Movie.MovieBuilder(id: movieId,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
                countries: [new Country(1, null), new Country(2, null), new Country(3, null)],
                genres: [new Genre(1, null), new Genre(2, null)],
                reviews: null
        ).build()
        when(movieDao.getById(movieId)).thenReturn(updatedMovie)
        when(enrichmentService.enrich(updatedMovie)).thenReturn(updatedMovie)
        assert updatedMovie == movieService.partialUpdate(movieId, updates)
        verify(movieDao).partialUpdate(movieId, updates.getMovieUpdates())
        verify(posterService).update(movieId, updates.poster)
        verify(countryService).updateLinks(movieId, updates.countries)
        verify(genreService).updateLinks(movieId, updates.genres)
    }

    @Test
    void testAdd() {
        def countries = [Country.create(10L, "USA"), Country.create(20, "GB")]
        def genres = [Genre.create(100L, "FirstGenre")]
        def reviews = [new Review.ReviewBuilder(
                id: 1000,
                text: "Great!",
                user: new User.UserBuilder(id:  50, nickname: "Big Ben").build()
        ).build()]
        def movie = new Movie.MovieBuilder(
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
        ).build()

        long movieId = 11L

        def addedMovie = new Movie.MovieBuilder(id: movieId,
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
        ).build()

        when(movieDao.add(movie)).thenReturn(movieId)
        when(movieDao.getById(movieId)).thenReturn(addedMovie)
        when(enrichmentService.enrich(addedMovie)).thenReturn(addedMovie)
        assert addedMovie == movieService.add(movie)
        verify(movieDao).add(movie)
        verify(posterService).link(movieId, movie.getPicturePath())
        verify(countryService).link(movieId, countries)
        verify(genreService).link(movieId, genres)

    }

    @Test
    void testGetByIdAndConvert() {

        def movie = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
        ).build()
        when(movieDao.getById(1L)).thenReturn(movie)
        when(enrichmentService.enrich(movie)).thenReturn(movie)
        when(currencyService.convert(movie.getPrice(), Currency.USD)).thenReturn(Double.valueOf(8.99 / 25))
        def actualMovieWithConvertedPrice = movieService.getById(1L, new RequestParameter(null, Currency.USD))
        assert Double.valueOf(8.99 / 25) == actualMovieWithConvertedPrice.getPrice()
    }

    @Test
    void testGetById() {
        def movie = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
                countries: null,
                genres: null,
                reviews: null
        ).build()

        when(movieDao.getById(1L)).thenReturn(movie)
        when(enrichmentService.enrich(movie)).thenReturn(movie)

        assert movie == movieService.getById(1L)
    }

    @Test
    void testGetAll() {

        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getAll()).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll()

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllByGenreId() {

        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getAll(1L)).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll(1L)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetThreeRandomMovies() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        def expectedMovies = [movieFirst, movieSecond]

        when(movieDao.getThreeRandomMovies()).thenReturn(expectedMovies)

        def actualMovies = movieService.getThreeRandomMovies()

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllAndSort() {

        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        def expectedMovies = [movieFirst, movieSecond]

        def sortDirection = new SortDirection("test", SortOrder.ASC)
        def requestParameter = new RequestParameter(sortDirection, null)

        when(movieDao.getAll(requestParameter)).thenReturn(expectedMovies)
        def actualMovies = movieService.getAll(requestParameter)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetAllByGenreIdAndSort() {

        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        def expectedMovies = [movieFirst, movieSecond]

        def sortDirection = new SortDirection("test", SortOrder.ASC)
        def requestParameter = new RequestParameter(sortDirection, null)

        when(movieDao.getAll(1L, requestParameter)).thenReturn(expectedMovies)

        def actualMovies = movieService.getAll(1L, requestParameter)

        assert expectedMovies == actualMovies
    }
}
