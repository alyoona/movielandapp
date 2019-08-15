package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class CachedJdbcGenreDaoTest {

    @Test
    void testGetAll() {
        def expectedGenres = [new Genre(id: 1L, name: "genreFirst"),
                              new Genre(id: 2L, name: "genreSecond")]

        def genreDao = mock(JdbcGenreDao.class)
        when(genreDao.getAll()).thenReturn(expectedGenres)

        def cachedJdbcGenreDao = new CachedJdbcGenreDao(genreDao)
        cachedJdbcGenreDao.updateGenres()
        def actualGenres = cachedJdbcGenreDao.getAll()

        assert expectedGenres == actualGenres
    }
}
