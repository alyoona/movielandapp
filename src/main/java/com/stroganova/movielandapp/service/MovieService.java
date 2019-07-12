package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getAll(Map<String, String> allParams);

    List<Movie> getAll(long genreId);

    List<Movie> getAll(long genreId, Map<String, String> allParams);

    List<Movie> getThreeRandomMovies();

}
