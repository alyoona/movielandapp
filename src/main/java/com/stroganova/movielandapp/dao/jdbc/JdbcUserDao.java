package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.UserDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.UserResultSetExtractor;
import com.stroganova.movielandapp.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private final static UserResultSetExtractor USER_RESULT_SET_EXTRACTOR = new UserResultSetExtractor();
    @NonNull private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull private final String getUserByEmailAndPasswordSql;
    @Override

    public User get(User user) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("email", user.getEmail());
        sqlParameterSource.addValue("password", user.getPassword());
        return namedParameterJdbcTemplate.query(getUserByEmailAndPasswordSql, sqlParameterSource, USER_RESULT_SET_EXTRACTOR);

    }
}
