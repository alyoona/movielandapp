package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieUpdateDirections;

import java.util.List;

public interface CountryService {

    List<Country> getAll(Movie movie);

    void add(long movieId, List<Country> countries);

    void update(long movieId, MovieUpdateDirections updates);
}
