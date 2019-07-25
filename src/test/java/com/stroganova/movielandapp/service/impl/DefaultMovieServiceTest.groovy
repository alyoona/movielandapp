package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.MovieDao
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.web.entity.SortDirection
import org.junit.Test

import java.time.LocalDate

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DefaultMovieServiceTest {

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

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll()).thenReturn(expectedMovies)

        def movieService = new DefaultMovieService(movieDao)

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

        def movieDao = mock(MovieDao.class)
        when(movieDao.getAll(1L)).thenReturn(expectedMovies)

        def movieService = new DefaultMovieService(movieDao)

        def actualMovies = movieService.getAll(1L)

        assert expectedMovies == actualMovies
    }

    @Test
    void testGetThreeRandomMovies(){
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

        def movieDao = mock(MovieDao.class)
        when(movieDao.getThreeRandomMovies()).thenReturn(expectedMovies)

        def movieService = new DefaultMovieService(movieDao)

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

        def movieDao = mock(MovieDao.class)

        def sortDirection = new SortDirection()

        when(movieDao.getAll(sortDirection)).thenReturn(expectedMovies)

        def movieService = new DefaultMovieService(movieDao)

        def actualMovies = movieService.getAll(sortDirection)

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

        def movieDao = mock(MovieDao.class)
        def sortDirection = new SortDirection()
        when(movieDao.getAll(1L, sortDirection)).thenReturn(expectedMovies)

        def movieService = new DefaultMovieService(movieDao)

        def actualMovies = movieService.getAll(1L, sortDirection)

        assert expectedMovies == actualMovies
    }
}
