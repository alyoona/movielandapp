package com.stroganova.movielandapp.dao.cache

import com.stroganova.movielandapp.dao.cache.impl.DefaultCachedGenreDao
import com.stroganova.movielandapp.dao.jdbc.JdbcGenreDao
import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DefaultCachedGenreDaoTest {

    @Test
    void testGetAll() {

        def expectedGenres = [new Genre(1L, "genreFirst"),
                              new Genre(2L, "genreSecond")]

        def genreDao = mock(JdbcGenreDao.class)
        when(genreDao.getAll()).thenReturn(expectedGenres)

        def cachedJdbcGenreDao = new DefaultCachedGenreDao(genreDao)
        cachedJdbcGenreDao.invalidate()
        def actualGenres = cachedJdbcGenreDao.getAll()

        assert expectedGenres == actualGenres
    }
}
