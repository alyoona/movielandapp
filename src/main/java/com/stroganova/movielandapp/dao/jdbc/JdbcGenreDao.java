package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.GenreRowMapper;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JdbcGenreDao implements GenreDao {

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();
    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String getAllGenresSql;
    @NonNull String getAllGenresByMovieIdSql;
    @NonNull String movieGenreInsertSql;

    @Override
    public List<Genre> getAll() {
        log.info("Get all genres from DB");
        List<Genre> genres = namedParameterJdbcTemplate.query(getAllGenresSql, genreRowMapper);
        if (genres.size() == 0) {
            log.warn("Genres cache is empty, there are not genres in DB");
        }
        return genres;
    }

    @Override
    public List<Genre> getAll(Movie movie) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", movie.getId());
        return namedParameterJdbcTemplate.query(getAllGenresByMovieIdSql, sqlParameterSource, genreRowMapper);
    }

    @Override
    public void add(long movieId, List<Genre> genres) {
        List<Map<String, Object>> valueMaps = new ArrayList<>();
        for (Genre genre : genres) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("movie_id", movieId);
            valueMap.put("genre_id", genre.getId());
            valueMaps.add(valueMap);
        }
        namedParameterJdbcTemplate.batchUpdate(movieGenreInsertSql, SqlParameterSourceUtils.createBatch(valueMaps));
    }

}
