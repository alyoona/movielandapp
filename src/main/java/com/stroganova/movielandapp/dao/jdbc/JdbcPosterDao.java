package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.PosterDao;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JdbcPosterDao implements PosterDao {

    @NonNull NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @NonNull String posterInsertSql;

    @Override
    public void add(long movieId, String picturePath) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("movie_id", movieId);
        sqlParameterSource.addValue("picture_path", picturePath);
        namedParameterJdbcTemplate.update(posterInsertSql, sqlParameterSource);
    }
}
