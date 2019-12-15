package com.stroganova.movielandapp.config.root.queryconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenreSqlQueryConfig {
    @Bean
    public String getAllGenresSql() {
        return "SELECT id, name FROM movieland.genre;";
    }

    @Bean
    public String getAllGenresByMovieIdSql() {
        return "SELECT g.id, g.name" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.movie_genre mg ON m.id = mg.movie_id" +
                "        JOIN movieland.genre g ON mg.genre_id = g.id" +
                "        WHERE m.id = :id;";
    }

    @Bean
    public String movieGenreInsertSql() {
        return "INSERT INTO movieland.movie_genre (movie_id, genre_id)" +
                "        VALUES (:movie_id, :genre_id);";
    }

    @Bean
    public String movieGenreDeleteSql() {
        return "DELETE FROM movieland.movie_genre" +
                "        WHERE movie_id = :movie_id;";
    }
}
