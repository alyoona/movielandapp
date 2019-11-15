package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.UserDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.UserResultSetExtractor;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.entity.UserCredentials;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private final static UserResultSetExtractor USER_RESULT_SET_EXTRACTOR = new UserResultSetExtractor();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String getUserByEmailAndPasswordSql;
    @Override

    public User get(UserCredentials userCredentials) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("email", userCredentials.getEmail());
        sqlParameterSource.addValue("password", userCredentials.getPassword());
        return namedParameterJdbcTemplate.query(getUserByEmailAndPasswordSql, sqlParameterSource, USER_RESULT_SET_EXTRACTOR);

    }
}
