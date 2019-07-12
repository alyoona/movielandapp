package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Movie
import org.junit.Test

import java.time.LocalDate

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieServiceDefaultTest {

    @Test
    void testGetAll() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussian")
        movieFirst.setNameNative("NameNative")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussian")
        movieSecond.setNameNative("NameNative")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def expectedMovies = [movieFirst, movieSecond]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll()).thenReturn(expectedMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def actualMovies = movieService.getAll()

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllByGenreId() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussian")
        movieFirst.setNameNative("NameNative")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussian")
        movieSecond.setNameNative("NameNative")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def expectedMovies = [movieFirst, movieSecond]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll(1L)).thenReturn(expectedMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def actualMovies = movieService.getAll(1L)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetThreeRandomMovies() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussian")
        movieFirst.setNameNative("NameNative")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussian")
        movieSecond.setNameNative("NameNative")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def movieDao = mock(MovieDao.class)
        def movieService = new MovieServiceDefault(movieDao)

        def allMoviesLessThanThree = [movieFirst, movieSecond]
        when(movieDao.getAll()).thenReturn(allMoviesLessThanThree)
        def actualRandomMoviesWhenAllMoviesLessThanThree = movieService.getThreeRandomMovies()
        assertEquals(3, actualRandomMoviesWhenAllMoviesLessThanThree.size())

        def allMoviesEqualsThree = [movieFirst, movieSecond, movieSecond]
        when(movieDao.getAll()).thenReturn(allMoviesEqualsThree)
        def actualRandomMoviesWhenAllMoviesEqualsThree = movieService.getThreeRandomMovies()
        assertEquals(3, actualRandomMoviesWhenAllMoviesEqualsThree.size())

        def allMoviesMoreThanThree = [movieFirst, movieSecond, movieFirst, movieFirst]
        when(movieDao.getAll()).thenReturn(allMoviesMoreThanThree)
        def actualRandomMoviesWhenAllMoviesMoreThanThree = movieService.getThreeRandomMovies()
        assertEquals(3, actualRandomMoviesWhenAllMoviesMoreThanThree.size())
    }


    @Test
    void testGetAllAndSortByRatingDecs() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setRating(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setRating(9D)

        def initialMovies = [movieFirst, movieSecond]
        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll()).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["rating": "desc"]
        def actualMovies = movieService.getAll(params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByRatingDecs() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setRating(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setRating(9D)

        def initialMovies = [movieFirst, movieSecond]
        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll(3L)).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["rating": "desc"]
        def actualMovies = movieService.getAll(3L, params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllAndSortByPriceDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(9D)

        def initialMovies = [movieFirst, movieSecond]
        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll()).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["price": "desc"]
        def actualMovies = movieService.getAll(params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByPriceDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(9D)

        def initialMovies = [movieFirst, movieSecond]
        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll(3L)).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["price": "desc"]
        def actualMovies = movieService.getAll(3L, params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllAndSortByPriceAsc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(9D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8.99D)

        def initialMovies = [movieFirst, movieSecond]

        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll()).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["price": "asc"]
        def actualMovies = movieService.getAll(params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByPriceAsc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(9D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8.99D)

        def initialMovies = [movieFirst, movieSecond]

        def expectedMovies = [movieSecond, movieFirst]

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll(3L)).thenReturn(initialMovies)

        def movieService = new MovieServiceDefault(movieDao)

        def params = ["price": "asc"]
        def actualMovies = movieService.getAll(3L, params)

        actualMovies.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index], actualMovie)
        }
    }


    @Test
    void testNegativeSortByRatingAscNotSupported() {
        def movieDao = mock(MovieDao.class)
        def movieService = new MovieServiceDefault(movieDao)
        def params = ["rating": "asc"]
        try {
            movieService.getAll(3L, params)
            fail()
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Sorting by rating(acs) is not supported")
        }
    }

    @Test
    void testNegativeSortByIncorrectRequestParamName() {
        def movieDao = mock(MovieDao.class)
        def movieService = new MovieServiceDefault(movieDao)
        def params = ["incorrectRequestParamName": "asc"]
        try {
            movieService.getAll(3L, params)
            fail()
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "No movie request parameter for name: incorrectRequestParamName")
        }
    }

    @Test
    void testNegativeSortByIncorrectRequestParamValue() {
        def movieDao = mock(MovieDao.class)
        def movieService = new MovieServiceDefault(movieDao)
        def params = ["rating": "incorrectRequestParamValue"]
        try {
            movieService.getAll(params)
            fail()
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "No sorting order for value: incorrectRequestParamValue")
        }
    }


}
