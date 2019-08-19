package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class GenreRowMapperTest {

    @Test
    void testMapRow() {
        def expectedGenre = new Genre(1L, "genreName")


        def resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(expectedGenre.getId())
        when(resultSet.getString("name")).thenReturn(expectedGenre.getName())

        def genreRowMapper = new GenreRowMapper()
        def actualGenre = genreRowMapper.mapRow(resultSet,0)

        assertEquals(expectedGenre.getId(), actualGenre.getId())
        assertEquals(expectedGenre.getName(), actualGenre.getName())
    }
}
