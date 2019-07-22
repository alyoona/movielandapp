package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.GenreDao
import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class DefaultGenreServiceTest {

    @Test
    void testGetAll() {
        def expectedGenres = [new Genre(id: 1L, name: "genreFirstName"),
                              new Genre(id: 1L, name: "genreFirstName")]

        def genreDao = mock(GenreDao.class)
        when(genreDao.getAll()).thenReturn(expectedGenres)

        def genreService = new DefaultGenreService(genreDao)

        def actualGenres = genreService.getAll()

        assert expectedGenres == actualGenres
    }
}
