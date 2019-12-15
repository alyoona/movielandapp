package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.MovieDetailsResultSetExtractor;
import com.stroganova.movielandapp.dao.jdbc.mapper.MovieRowMapper;
import com.stroganova.movielandapp.dao.jdbc.util.QueryBuilder;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieFieldUpdate;
import com.stroganova.movielandapp.request.MovieRequestParameterList;
import com.stroganova.movielandapp.request.SortDirection;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcMovieDao implements MovieDao {

    private final static MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final static MovieDetailsResultSetExtractor MOVIE_DETAILS_RESULT_SET_EXTRACTOR =
            new MovieDetailsResultSetExtractor(MOVIE_ROW_MAPPER);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String getAllMoviesSql;
    private final String getThreeRandomMoviesSql;
    private final String getMoviesByGenreIdSql;
    private final String getMovieByIdSql;
    private final String movieInsertSql;

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
    public List<Movie> getAll(MovieRequestParameterList movieRequestParameterList) {
        SortDirection sortDirection = movieRequestParameterList.getSortDirection();
        String getAllMoviesAndSortDirectionSql = QueryBuilder.getOrderBySql(getAllMoviesSql, sortDirection);
        return namedParameterJdbcTemplate.query(getAllMoviesAndSortDirectionSql, EmptySqlParameterSource.INSTANCE, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getAll(long genreId, MovieRequestParameterList movieRequestParameterList) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("genre_id", genreId);
        SortDirection sortDirection = movieRequestParameterList.getSortDirection();
        String getMoviesByGenreIdAndSortDirectionSql = QueryBuilder.getOrderBySql(getMoviesByGenreIdSql, sortDirection);
        return namedParameterJdbcTemplate.query(getMoviesByGenreIdAndSortDirectionSql, sqlParameterSource, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return namedParameterJdbcTemplate.query(getThreeRandomMoviesSql, MOVIE_ROW_MAPPER);
    }

    @Override
    public Movie getById(long movieId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", movieId);
        return namedParameterJdbcTemplate.query(getMovieByIdSql, sqlParameterSource, MOVIE_DETAILS_RESULT_SET_EXTRACTOR);
    }

    @Override
    public long add(Movie movie) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name_russian", movie.getNameRussian());
        sqlParameterSource.addValue("name_native", movie.getNameNative());
        sqlParameterSource.addValue("year", movie.getYearOfRelease());
        sqlParameterSource.addValue("description", movie.getDescription());
        sqlParameterSource.addValue("rating", movie.getRating());
        sqlParameterSource.addValue("price", movie.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(movieInsertSql, sqlParameterSource, keyHolder);
        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void partialUpdate(long movieId, Map<MovieFieldUpdate, Object> updates) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", movieId);
        for (Map.Entry<MovieFieldUpdate, Object> field : updates.entrySet()) {
            MovieFieldUpdate movieFieldUpdate = field.getKey();
            sqlParameterSource.addValue(movieFieldUpdate.getDbName(), field.getValue());
        }
        namedParameterJdbcTemplate.update(QueryBuilder.getUpdateSql(new ArrayList<>(updates.keySet())), sqlParameterSource);
    }

    @Override
    public void update(Movie movie) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", movie.getId());
        sqlParameterSource.addValue("name_russian", movie.getNameRussian());
        sqlParameterSource.addValue("name_native", movie.getNameNative());
        sqlParameterSource.addValue("year", movie.getYearOfRelease());
        sqlParameterSource.addValue("description", movie.getDescription());
        sqlParameterSource.addValue("rating", movie.getRating());
        sqlParameterSource.addValue("price", movie.getPrice());
        namedParameterJdbcTemplate.update(QueryBuilder.getAllMovieFieldsUpdateSql(), sqlParameterSource);
    }


}
