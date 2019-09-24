package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.PosterDao;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JdbcPosterDao implements PosterDao {

    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String posterInsertSql;
    @NonNull String posterUpdateSql;

    @Override
    public void add(long movieId, String picturePath) {
        namedParameterJdbcTemplate.update(posterInsertSql, getSqlParameterSource(movieId, picturePath));
    }

    @Override
    public void update(long movieId, String picturePath) {
        namedParameterJdbcTemplate.update(posterUpdateSql, getSqlParameterSource(movieId, picturePath));
    }

    private SqlParameterSource getSqlParameterSource(long movieId, String picturePath) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("movie_id", movieId);
        sqlParameterSource.addValue("picture_path", picturePath);
        return sqlParameterSource;
    }


}
