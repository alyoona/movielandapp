package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getAll(long genreId);

    List<Movie> getThreeRandomMovies();

}
