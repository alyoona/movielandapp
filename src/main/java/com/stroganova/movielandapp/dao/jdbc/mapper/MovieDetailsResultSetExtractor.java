package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDetailsResultSetExtractor implements ResultSetExtractor<Movie> {
    private final RowMapper<Movie> movieRowMapper;
    public MovieDetailsResultSetExtractor(RowMapper<Movie> movieRowMapper) {
        this.movieRowMapper = movieRowMapper;
    }

    @Override
    public Movie extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()){
            return new Movie.MovieBuilder()
                    .newMovie(movieRowMapper.mapRow(rs, 0))
                    .setDescription(rs.getString("description"))
                    .build();
        }
        return null;
    }
}
