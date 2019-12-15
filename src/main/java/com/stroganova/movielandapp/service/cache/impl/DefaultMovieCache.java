package com.stroganova.movielandapp.service.cache.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.exception.EntityNotFoundException;
import com.stroganova.movielandapp.service.enrichment.MovieEnrichmentService;
import com.stroganova.movielandapp.service.cache.MovieCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class DefaultMovieCache implements MovieCache {
    private final MovieDao movieDao;
    private final Map<Long, Reference<Movie>> cache = new ConcurrentHashMap<>();
    private final MovieEnrichmentService enrichmentService;

    @Override
    public Movie getById(long movieId) {

        Reference<Movie> movieReference = cache.get(movieId);

        if (movieReference != null) {
            Movie movie = movieReference.get();
            if (movie == null) {
                movieReference = cache.computeIfPresent(movieId, (key, currentMovieReference) -> findAndEnrich(key));
                return movieReference.get();
            }
            return movie;
        } else {
            movieReference = cache.computeIfAbsent(movieId, this::findAndEnrich);
            return movieReference.get();
        }

    }

    private Reference<Movie> findAndEnrich(long movieId) {
        Movie movie = movieDao.getById(movieId);
        if (movie == null) {
            throw new EntityNotFoundException("No such movie");
        }
        return new SoftReference<>(enrichmentService.enrich(movie));
    }

    @Override
    public void invalidateCachedMovie(long movieId) {
        cache.computeIfPresent(movieId, (key, ref) -> findAndEnrich(key));
    }
}
