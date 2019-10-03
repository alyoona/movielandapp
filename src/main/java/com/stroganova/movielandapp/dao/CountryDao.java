package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;

import java.util.List;

public interface CountryDao {

    List<Country> getAll(Movie movie);

    void link(long movieId, List<Country> countries);

    void deleteAllLinks(long movieId);
}
