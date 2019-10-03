package com.stroganova.movielandapp.config.root.queryConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieSqlQueryConfig {
    @Bean
    public String getAllMoviesSql() {
        return "SELECT m.id, m.name_russian, m.name_native, m.year, m.rating, m.price, p.picture_path " +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.poster p ON m.id = p.movie_id";
    }

    @Bean
    public String getThreeRandomMoviesSql() {
        return "SELECT m.id, m.name_russian, m.name_native, m.year, m.rating, m.price, p.picture_path" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.poster p ON m.id = p.movie_id" +
                "        ORDER BY RANDOM() LIMIT 3;";
    }

    @Bean
    public String getMoviesByGenreIdSql() {
        return "SELECT m.id, m.name_russian, m.name_native, m.year, m.rating, m.price, p.picture_path" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.poster p ON m.id = p.movie_id" +
                "        JOIN  movieland.movie_genre mg ON m.id = mg.movie_id" +
                "        WHERE mg.genre_id = :genre_id";
    }

    @Bean
    public String getMovieByIdSql() {
        return "SELECT m.id, m.name_russian, m.name_native, m.year, m.rating, m.price, p.picture_path, m.description" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.poster p ON m.id = p.movie_id" +
                "        WHERE m.id = :id;";
    }

    @Bean
    public String movieInsertSql() {
        return "INSERT INTO movieland.movie (name_russian, name_native, year, description, rating, price)" +
                "        VALUES (:name_russian, :name_native, :year, :description, :rating, :price);";
    }
}
