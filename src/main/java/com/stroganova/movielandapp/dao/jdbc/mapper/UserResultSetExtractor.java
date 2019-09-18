package com.stroganova.movielandapp.dao.jdbc.mapper;

import com.stroganova.movielandapp.entity.Role;
import com.stroganova.movielandapp.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetExtractor implements ResultSetExtractor<User> {

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        return rs.next() ? mapRow(rs) : null;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setNickname(rs.getString("first_name") + " " + rs.getString("last_name"));
        user.setRole(Role.valueOf(rs.getString("role_name")));
        return user;
    }
}
