package com.stroganova.movielandapp.config.root.queryconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewSqlQueryConfig {
    @Bean
    public String getAllReviewsByMovieIdSql() {
        return "SELECT r.id, r.description, r.user_id, u.first_name, u.last_name" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.review r ON m.id = r.movie_id" +
                "        JOIN movieland.users u ON r.user_id = u.id" +
                "        WHERE m.id = :id;";
    }

    @Bean
    public String addReviewSql() {
        return "INSERT INTO movieland.review (user_id, movie_id, description)" +
                "        VALUES (:user_id, :movie_id, :description);";
    }
}
