package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.GenreRowMapper;
import com.stroganova.movielandapp.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao{

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getAllGenresSql;

    public JdbcGenreDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcTemplate.query(getAllGenresSql, genreRowMapper);
    }

    @Autowired
    public void setGetAllGenresSql(String getAllGenresSql) {
        this.getAllGenresSql = getAllGenresSql;
    }
}
