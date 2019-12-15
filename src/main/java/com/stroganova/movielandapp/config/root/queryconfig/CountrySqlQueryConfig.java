package com.stroganova.movielandapp.config.root.queryconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountrySqlQueryConfig {
    @Bean
    public String getAllCountriesByMovieIdSql() {
        return "SELECT c.id, c.name" +
                "        FROM movieland.movie m" +
                "        LEFT JOIN movieland.movie_country mc ON m.id = mc.movie_id" +
                "        JOIN movieland.country c ON mc.country_id = c.id" +
                "        WHERE m.id = :id;";
    }

    @Bean
    public String countryInsertSql() {
        return "INSERT INTO movieland.country (name) VALUES (:name);";
    }

    @Bean
    public String movieCountryInsertSql() {
        return "INSERT INTO movieland.movie_country (movie_id, country_id)" +
                "        VALUES (:movie_id, :country_id);";
    }

    @Bean
    public String movieCountryDeleteSql() {
        return "DELETE FROM movieland.movie_country" +
                "        WHERE movie_id = :movie_id;";
    }
}
