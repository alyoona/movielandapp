package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Movie
import org.junit.Test

import java.time.LocalDate

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieServiceDefaultTest  {

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
            assertEquals(expectedMovies[index], actualMovie )
        }
    }

    @Test
    void testGetThreeRandomMovies(){
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
}
