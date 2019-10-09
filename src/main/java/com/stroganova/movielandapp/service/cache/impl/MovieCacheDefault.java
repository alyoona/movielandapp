package com.stroganova.movielandapp.service.cache.impl;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.cache.MovieCache;
import org.springframework.stereotype.Service;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieCacheDefault implements MovieCache {

    private final Map<Long, Reference<Movie>> cache = new ConcurrentHashMap<>();

    @Override
    public Movie getById(long id) {
        Reference<Movie> movieReference = cache.get(id);
        Movie movie = null;
        if(movieReference != null) {
            movie = movieReference.get();
            if(movie == null) {
                cache.remove(id);
            }
        }
        return  movie;
    }
    @Override
    public void cacheMovie(Movie movie) {
        Reference<Movie> movieReference = new SoftReference<>(movie);
        cache.put(movie.getId(), movieReference);

    }

    @Override
    public void invalidateCachedMovie(long id) {

    }


}
