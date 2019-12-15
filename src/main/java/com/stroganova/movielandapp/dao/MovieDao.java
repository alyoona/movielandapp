package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieFieldUpdate;
import com.stroganova.movielandapp.request.MovieRequestParameterList;

import java.util.List;
import java.util.Map;

public interface MovieDao {

    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    List<Movie> getAll(long genreId);

    List<Movie> getAll(MovieRequestParameterList movieRequestParameterList);

    List<Movie> getAll(long genreId, MovieRequestParameterList movieRequestParameterList);

    Movie getById(long movieId);

    long add(Movie movie);

    void partialUpdate(long movieId, Map<MovieFieldUpdate, Object> updates);

    void update(Movie movie);
}
