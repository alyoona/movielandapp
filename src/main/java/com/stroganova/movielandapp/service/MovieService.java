package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieRequestParameterList;
import com.stroganova.movielandapp.request.MovieUpdateDirections;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getAll(long genreId);

    List<Movie> getThreeRandomMovies();

    List<Movie> getAll(MovieRequestParameterList movieRequestParameterList);

    List<Movie> getAll(long genreId, MovieRequestParameterList movieRequestParameterList);

    Movie getById(long movieId);

    Movie getById(long movieId, MovieRequestParameterList movieRequestParameterList);

    Movie add(Movie movie);

    Movie partialUpdate(long id, MovieUpdateDirections updates);

    Movie update(Movie movie);

}
