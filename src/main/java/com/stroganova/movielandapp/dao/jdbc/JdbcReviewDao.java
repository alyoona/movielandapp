package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.ReviewDao;
import com.stroganova.movielandapp.dao.jdbc.mapper.ReviewRowMapper;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JdbcReviewDao implements ReviewDao {

    private final static ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String getAllReviewsByMovieIdSql;
    private final String addReviewSql;

    @Override
    public List<Review> getAll(Movie movie) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", movie.getId());
        return namedParameterJdbcTemplate.query(getAllReviewsByMovieIdSql, sqlParameterSource, REVIEW_ROW_MAPPER);
    }

    @Override
    public void add(Review review) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("user_id", review.getUser().getId());
        sqlParameterSource.addValue("movie_id", review.getMovie().getId());
        sqlParameterSource.addValue("description", review.getText());
        namedParameterJdbcTemplate.update(addReviewSql, sqlParameterSource);
    }
}
