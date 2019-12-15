package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.GenreDao
import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.service.GenreService
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when


class DefaultGenreServiceTest {

    private GenreService genreService
    private GenreDao genreDao

    @Before
    void before() {
        genreDao = mock(GenreDao.class)
        genreService = new DefaultGenreService(genreDao)

    }

    @Test
    void testUpdate() {
        def genres = [Genre.create(1), Genre.create(2)]
        long movieId = 26L
        genreService.updateLinks(movieId, genres)
        verify(genreDao).deleteAllLinks(movieId)
        verify(genreDao).link(movieId, [Genre.create(1), Genre.create(2)])
    }

    @Test
    void testAdd() {
        long movieId = 22L
        def genres = [Genre.create(1L, "genreFirstName"),
                      Genre.create(1L, "genreFirstName")]
        genreService.link(movieId, genres)
        verify(genreDao).link(movieId, genres)
    }


    @Test
    void testGetAll() {
        def expectedGenres = [Genre.create(1L, "genreFirstName"),
                              Genre.create(1L, "genreFirstName")]

        when(genreDao.getAll()).thenReturn(expectedGenres)
        def actualGenres = genreService.getAll()
        assert expectedGenres == actualGenres
    }

    @Test
    void testGetAllByMovie() {
        def expectedGenres = [Genre.create(1L, "genreFirstName"),
                              Genre.create(1L, "genreFirstName")]

        when(genreDao.getAll(new Movie.MovieBuilder(id: 10L).build())).thenReturn(expectedGenres)
        def actualGenres = genreService.getAll(new Movie.MovieBuilder(id: 10L).build())
        assert expectedGenres == actualGenres
    }

}
