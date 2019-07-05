package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.MovieRowMapper;
import com.stroganova.movielandapp.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private final static MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getAllMoviesSql;

    public JdbcMovieDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        return namedParameterJdbcTemplate.query(getAllMoviesSql, MOVIE_ROW_MAPPER);
    }

    @Autowired
    public void setGetAllMoviesSql(String getAllMoviesSql) {
        this.getAllMoviesSql = getAllMoviesSql;
    }
}
