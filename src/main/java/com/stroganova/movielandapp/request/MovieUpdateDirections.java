package com.stroganova.movielandapp.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.web.json.deseializer.MovieUpdateDirectionsDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonDeserialize(using = MovieUpdateDirectionsDeserializer.class)
public class MovieUpdateDirections {

    private final Map<MovieFieldUpdate, Object> map;

    public Map<MovieFieldUpdate, Object> getMovieUpdates() {
        Map<MovieFieldUpdate, Object> copy = new HashMap<>(map);
        copy.remove(MovieFieldUpdate.PICTURE_PATH);
        copy.remove(MovieFieldUpdate.COUNTRIES);
        copy.remove(MovieFieldUpdate.GENRES);
        copy.remove(MovieFieldUpdate.REVIEWS);
        return copy;
    }

    public String getPoster() {
        return (String) map.get(MovieFieldUpdate.PICTURE_PATH);
    }

    public List<Country> getCountries() {
        @SuppressWarnings("unchecked")
        List<Country> countries = (List<Country>) map.get(MovieFieldUpdate.COUNTRIES);
        return countries;
    }

    public List<Genre> getGenres() {
        @SuppressWarnings("unchecked")
        List<Genre> genres = (List<Genre>) map.get(MovieFieldUpdate.GENRES);
        return genres;
    }




}
