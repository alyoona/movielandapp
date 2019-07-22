package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.GenreRowMapper;
import com.stroganova.movielandapp.entity.Genre;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class JdbcGenreDao implements GenreDao{

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();
    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String getAllGenresSql;

    @Scheduled(fixedRate = 4*60*60*1000)
    @CachePut(cacheNames = "genres")
    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcTemplate.query(getAllGenresSql, genreRowMapper);
    }

}
