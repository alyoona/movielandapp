package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieUpdateDirections;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    List<Genre> getAll(Movie movie);

    void link(long movieId, List<Genre> genres);

    void updateLinks(long movieId, List<Genre> genres);
}
