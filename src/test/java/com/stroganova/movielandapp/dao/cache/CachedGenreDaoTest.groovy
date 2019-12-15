package com.stroganova.movielandapp.dao.cache

import com.stroganova.movielandapp.dao.jdbc.JdbcGenreDao
import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class CachedGenreDaoTest {

    @Test
    void testGetAll() {

        def expectedGenres = [Genre.create(1L, "genreFirst"),
                              Genre.create(2L, "genreSecond")]

        def genreDao = mock(JdbcGenreDao.class)
        when(genreDao.getAll()).thenReturn(expectedGenres)

        def cachedGenreDao = new CachedGenreDao(genreDao)
        cachedGenreDao.invalidate()
        def actualGenres = cachedGenreDao.getAll()

        assert expectedGenres == actualGenres
    }
}
