package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Movie.MovieBuilder()
        .setId(resultSet.getLong("id"))
        .setNameRussian(resultSet.getString("name_russian"))
        .setNameNative(resultSet.getString("name_native"))
        .setYearOfRelease(resultSet.getDate("year").toLocalDate())
        .setRating(resultSet.getDouble("rating"))
        .setPrice(resultSet.getDouble("price"))
        .setPicturePath(resultSet.getString("picture_path"))
                .build();
    }
}
