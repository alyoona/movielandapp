package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Genre
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class CountryRowMapperTest {
    @Test
    void testMapRow() {
        def expectedCountry = new Genre(1L, "CountryName")

        def resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(expectedCountry.getId())
        when(resultSet.getString("name")).thenReturn(expectedCountry.getName())

        def countryRowMapper = new CountryRowMapper()
        def actualCountry = countryRowMapper.mapRow(resultSet,0)

        assertEquals(expectedCountry.getId(), actualCountry.getId())
        assertEquals(expectedCountry.getName(), actualCountry.getName())
    }
}
