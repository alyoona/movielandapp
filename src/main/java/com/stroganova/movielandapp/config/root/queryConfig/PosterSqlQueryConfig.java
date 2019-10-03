package com.stroganova.movielandapp.config.root.queryConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PosterSqlQueryConfig {
    @Bean
    public String posterInsertSql() {
        return "INSERT INTO movieland.poster (movie_id, picture_path)" +
                "        VALUES (:movie_id, :picture_path)";
    }

    @Bean
    public String posterUpdateSql() {
        return "UPDATE movieland.poster" +
                "        SET picture_path = :picture_path" +
                "        WHERE movie_id = :movie_id;";
    }
}
