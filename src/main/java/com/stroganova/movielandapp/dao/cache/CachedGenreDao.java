package com.stroganova.movielandapp.dao.cache;

import com.stroganova.movielandapp.entity.Genre;

import java.util.List;

public interface CachedGenreDao {

    List<Genre> getAll();
}
