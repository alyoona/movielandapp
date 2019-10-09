package com.stroganova.movielandapp.service.cache;

import com.stroganova.movielandapp.entity.Movie;

public interface MovieCache {

    Movie getById(long id);

    void cacheMovie(Movie movie);

    void invalidateCachedMovie(long id);
}
