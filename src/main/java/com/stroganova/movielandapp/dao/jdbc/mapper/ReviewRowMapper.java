package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        String nickname = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
        User user = new User.UserBuilder()
                .setId(resultSet.getLong("user_id"))
                .setNickname(nickname)
                .build();

        return new Review.ReviewBuilder()
                .setId(resultSet.getLong("id"))
                .setText(resultSet.getString("description"))
                .setUser(user)
                .build();
    }
}
