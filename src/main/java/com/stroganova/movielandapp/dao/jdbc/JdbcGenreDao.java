package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.GenreRowMapper;
import com.stroganova.movielandapp.entity.Genre;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class JdbcGenreDao implements GenreDao{

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();
    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String getAllGenresSql;
    @NonNull String getAllGenresByMovieIdSql;

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
    public List<Genre> getAll(long movieId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", movieId);
        return namedParameterJdbcTemplate.query(getAllGenresByMovieIdSql, sqlParameterSource, genreRowMapper);
    }

}
