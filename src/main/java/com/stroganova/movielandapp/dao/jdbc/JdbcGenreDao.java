package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.GenreRowMapper;
import com.stroganova.movielandapp.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao{
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final GenreRowMapper genreRowMapper = new GenreRowMapper();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getAllGenresSql;

    public JdbcGenreDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Scheduled(fixedRate = 4*60*60*1000)
    @CachePut(cacheNames = "genres")
    @Override
    public List<Genre> getAll() {
        LOGGER.info("Get all genres from DB");
        return namedParameterJdbcTemplate.query(getAllGenresSql, genreRowMapper);
    }

    @Autowired
    public void setGetAllGenresSql(String getAllGenresSql) {
        this.getAllGenresSql = getAllGenresSql;
    }
}
