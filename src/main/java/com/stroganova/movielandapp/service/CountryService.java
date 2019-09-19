package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;

import java.util.List;

public interface CountryService {

    List<Country> getAll(Movie movie);

    void add(long movieId, List<Country> countries);
}
