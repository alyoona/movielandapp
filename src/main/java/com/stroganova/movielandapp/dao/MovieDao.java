package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.SortDirection;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    List<Movie> getAll(long genreId);

    List<Movie> getAll(SortDirection sortDirection);
    List<Movie> getAll(long genreId, SortDirection sortDirection);
}
