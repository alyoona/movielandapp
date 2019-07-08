package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Movie
import org.junit.Test

import java.sql.ResultSet
import java.time.LocalDate
import java.sql.Date

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieRowMapperTest {

    @Test
    void testMapRow() {

        def expectedMovie = new Movie()
        expectedMovie.setId(1)
        expectedMovie.setNameRussian("NameRussian")
        expectedMovie.setNameNative("NameNative")
        expectedMovie.setYearOfRelease(LocalDate.of(1994, 1, 1))
        expectedMovie.setRating(8.99D)
        expectedMovie.setPrice(150.15D)
        expectedMovie.setPicturePath("https://picture_path.png")

        def resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(expectedMovie.getId())
        when(resultSet.getString("name_russian")).thenReturn(expectedMovie.getNameRussian())
        when(resultSet.getString("name_native")).thenReturn(expectedMovie.getNameNative())
        when(resultSet.getDate("year")).thenReturn(Date.valueOf(expectedMovie.getYearOfRelease()))
        when(resultSet.getDouble("rating")).thenReturn(expectedMovie.getRating())
        when(resultSet.getDouble("price")).thenReturn(expectedMovie.getPrice())
        when(resultSet.getString("picture_path")).thenReturn(expectedMovie.getPicturePath())

        def movieRowMapper = new MovieRowMapper()
        def actualMovie = movieRowMapper.mapRow(resultSet,0)

        assertEquals(expectedMovie.getId(), actualMovie.getId())
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian())
        assertEquals(expectedMovie.getNameNative(),actualMovie.getNameNative())
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease())
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(),0.00)
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(),0.00)
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath())
    }
}
