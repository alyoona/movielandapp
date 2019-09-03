package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.CountryDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.CountryRowMapper;
import com.stroganova.movielandapp.entity.Country;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class JdbcCountryDao implements CountryDao {

    private final static CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String getAllCountriesByMovieIdSql;

    @Override
    public List<Country> getAll(long movieId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", movieId);
        return namedParameterJdbcTemplate.query(getAllCountriesByMovieIdSql, sqlParameterSource, COUNTRY_ROW_MAPPER);
    }
}
