package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.web.entity.SortDirection;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getAll(long genreId);

    List<Movie> getThreeRandomMovies();

    List<Movie> getAll(SortDirection sortDirection);

    List<Movie> getAll(long genreId, SortDirection sortDirection);
}
