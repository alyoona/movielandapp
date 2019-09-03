package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.RequestParameter;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getAll(long genreId);

    List<Movie> getThreeRandomMovies();

    List<Movie> getAll(RequestParameter requestParameter);

    List<Movie> getAll(long genreId, RequestParameter requestParameter);

    Movie getById(long movieId);
}
