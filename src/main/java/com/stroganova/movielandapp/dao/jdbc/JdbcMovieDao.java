package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.MovieRowMapper;
import com.stroganova.movielandapp.entity.Movie;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class JdbcMovieDao implements MovieDao {

    private final static MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String getAllMoviesSql;
    @NonNull String getThreeRandomMoviesSql;
    @NonNull String getMoviesByGenreIdSql;

    @Override
    public List<Movie> getAll() {
        return namedParameterJdbcTemplate.query(getAllMoviesSql, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getAll(long genreId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("genre_id", genreId);
        return namedParameterJdbcTemplate.query(getMoviesByGenreIdSql, sqlParameterSource, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return namedParameterJdbcTemplate.query(getThreeRandomMoviesSql, MOVIE_ROW_MAPPER);
    }

}
