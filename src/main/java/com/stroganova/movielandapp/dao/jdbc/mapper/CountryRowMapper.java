package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong("id"));
        country.setName(resultSet.getString("name"));
        return country;
    }
}
