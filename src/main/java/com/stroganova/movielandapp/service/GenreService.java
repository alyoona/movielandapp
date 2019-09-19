package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    List<Genre> getAll(Movie movie);

    void add(long movieId, List<Genre> genres);
}
