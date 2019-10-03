package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieUpdateDirections;

import java.util.List;

public interface CountryService {

    List<Country> getAll(Movie movie);

    void link(long movieId, List<Country> countries);

    void updateLinks(long movieId, List<Country> countries);
}
