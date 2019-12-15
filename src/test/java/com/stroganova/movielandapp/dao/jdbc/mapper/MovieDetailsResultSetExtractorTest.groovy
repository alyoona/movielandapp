package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Movie
import org.junit.Test

import java.sql.Date
import java.sql.ResultSet
import java.time.LocalDate

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class MovieDetailsResultSetExtractorTest {
    def movieRowMapper = new MovieRowMapper()
    @Test
    void testExtractData() {

        def expectedMovie = new Movie.MovieBuilder(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1995, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "movie description",
                countries: [],
                genres: [],
                reviews: []
        ).build()

        def resultSet = mock(ResultSet.class)
        when(resultSet.next()).thenReturn(true)
        when(resultSet.getLong("id")).thenReturn(expectedMovie.getId())
        when(resultSet.getString("name_russian")).thenReturn(expectedMovie.getNameRussian())
        when(resultSet.getString("name_native")).thenReturn(expectedMovie.getNameNative())
        when(resultSet.getDate("year")).thenReturn(Date.valueOf(expectedMovie.getYearOfRelease()))
        when(resultSet.getDouble("rating")).thenReturn(expectedMovie.getRating())
        when(resultSet.getDouble("price")).thenReturn(expectedMovie.getPrice())
        when(resultSet.getString("picture_path")).thenReturn(expectedMovie.getPicturePath())
        when(resultSet.getString("description")).thenReturn(expectedMovie.getDescription())

        def movieDetailsResultSetExtractor = new MovieDetailsResultSetExtractor(movieRowMapper)
        def actualMovie = movieDetailsResultSetExtractor.extractData(resultSet)

        assert expectedMovie == actualMovie
        assert expectedMovie.id == actualMovie.id

    }
}
