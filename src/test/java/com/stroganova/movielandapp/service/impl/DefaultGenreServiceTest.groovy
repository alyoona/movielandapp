package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.cache.CachedGenreDao
import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class DefaultGenreServiceTest {

    @Test
    void testGetAll() {
        def expectedGenres = [new Genre(1L, "genreFirstName"),
                              new Genre(1L, "genreFirstName")]

        def cachedGenreDao = mock(CachedGenreDao.class)
        when(cachedGenreDao.getAll()).thenReturn(expectedGenres)

        def genreService = new DefaultGenreService(cachedGenreDao)

        def actualGenres = genreService.getAll()

        assert expectedGenres == actualGenres
    }
}
