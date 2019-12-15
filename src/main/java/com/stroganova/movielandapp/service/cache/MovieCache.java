package com.stroganova.movielandapp.service.cache;

import com.stroganova.movielandapp.entity.Movie;

public interface MovieCache {

    Movie getById(long id);

    void invalidateCachedMovie(long id);
}
