package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.CountryDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.CountryRowMapper;
import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcCountryDao implements CountryDao {

    private final static CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String getAllCountriesByMovieIdSql;
    private final String movieCountryInsertSql;
    private final String movieCountryDeleteSql;

    @Override
    public List<Country> getAll(Movie movie) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", movie.getId());
        return namedParameterJdbcTemplate.query(getAllCountriesByMovieIdSql, sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    @Override
    public void link(long movieId, List<Country> countries) {
        List<Map<String, Object>> valueMaps = new ArrayList<>();
        for (Country country : countries) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("movie_id", movieId);
            valueMap.put("country_id", country.getId());
            valueMaps.add(valueMap);
        }
        namedParameterJdbcTemplate.batchUpdate(movieCountryInsertSql, SqlParameterSourceUtils.createBatch(valueMaps));
    }

    @Override
    public void deleteAllLinks(long movieId) {
        namedParameterJdbcTemplate.update(movieCountryDeleteSql, new MapSqlParameterSource("movie_id", movieId));
    }
}
