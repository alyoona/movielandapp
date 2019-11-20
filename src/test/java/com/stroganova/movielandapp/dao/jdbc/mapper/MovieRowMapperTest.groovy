package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Movie
import org.junit.Test

import java.sql.ResultSet
import java.time.LocalDate
import java.sql.Date
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieRowMapperTest {

    @Test
    void testMapRow() {

        def expectedMovie = new Movie.MovieBuilder(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1995, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
                ).build()

        def resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(expectedMovie.getId())
        when(resultSet.getString("name_russian")).thenReturn(expectedMovie.getNameRussian())
        when(resultSet.getString("name_native")).thenReturn(expectedMovie.getNameNative())
        when(resultSet.getDate("year")).thenReturn(Date.valueOf(expectedMovie.getYearOfRelease()))
        when(resultSet.getDouble("rating")).thenReturn(expectedMovie.getRating())
        when(resultSet.getDouble("price")).thenReturn(expectedMovie.getPrice())
        when(resultSet.getString("picture_path")).thenReturn(expectedMovie.getPicturePath())

        def movieRowMapper = new MovieRowMapper()
        def actualMovie = movieRowMapper.mapRow(resultSet, 0)

        assert expectedMovie == actualMovie
        assert expectedMovie.id == actualMovie.id
    }
}
