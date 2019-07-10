package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.GenreDao
import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class GenreServiceDefaultTest{

    @Test
    void testGetAll() {
        def genreFirst = new Genre()
        genreFirst.setId(1L)
        genreFirst.setName("genreFirstName")
        def genreSecond = new Genre()
        genreSecond.setId(2L)
        genreSecond.setName("genreSecondName")

        def expectedGenres = [genreFirst, genreSecond]

        def genreDao = mock(GenreDao.class)
        when(genreDao.getAll()).thenReturn(expectedGenres)

        def genreService = new GenreServiceDefault(genreDao)

        def actualGenres = genreService.getAll()

        actualGenres.eachWithIndex { actualGenre, index ->
            assertEquals(expectedGenres[index], actualGenre )
        }
    }
}
