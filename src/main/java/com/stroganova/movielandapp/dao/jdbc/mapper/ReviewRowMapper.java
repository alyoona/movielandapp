package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setText(resultSet.getString("description"));

        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setNickname(resultSet.getString("first_name")+ " " + resultSet.getString("last_name"));
        review.setUser(user);

        return review;
    }
}
