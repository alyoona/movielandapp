package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.GenreDao
import com.stroganova.movielandapp.service.cache.GenreCache
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
    private GenreCache genreCache

    @Before
    void before() {
        genreCache = mock(GenreCache.class)
        genreDao = mock(GenreDao.class)
        genreService = new DefaultGenreService(genreCache, genreDao)

    }

    @Test
    void testUpdate() {
        def genres = [new Genre(1, null), new Genre(2, null)]
        long movieId = 26L
        genreService.updateLinks(movieId, genres)
        verify(genreDao).deleteAllLinks(movieId)
        verify(genreDao).link(movieId, [new Genre(1, null), new Genre(2, null)])
    }

    @Test
    void testAdd() {
        long movieId = 22L
        def genres = [new Genre(1L, "genreFirstName"),
                      new Genre(1L, "genreFirstName")]
        genreService.link(movieId, genres)
        verify(genreDao).link(movieId, genres)
    }


    @Test
    void testGetAll() {
        def expectedGenres = [new Genre(1L, "genreFirstName"),
                              new Genre(1L, "genreFirstName")]

        when(genreCache.getAll()).thenReturn(expectedGenres)
        def actualGenres = genreService.getAll()
        assert expectedGenres == actualGenres
    }

    @Test
    void testGetAllByMovie() {
        def expectedGenres = [new Genre(1L, "genreFirstName"),
                              new Genre(1L, "genreFirstName")]

        when(genreDao.getAll(new Movie(id: 10L))).thenReturn(expectedGenres)
        def actualGenres = genreService.getAll(new Movie(id: 10L))
        assert expectedGenres == actualGenres
    }

}
