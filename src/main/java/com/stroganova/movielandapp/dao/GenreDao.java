package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    List<Genre> getAll(Movie movie);

    void link(long movieId, List<Genre> genres);

    void deleteAllLinks(long movieId);
}
