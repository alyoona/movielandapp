package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.CountryService;
import com.stroganova.movielandapp.service.GenreService;
import com.stroganova.movielandapp.service.MovieEnrichmentService;
import com.stroganova.movielandapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParallelMovieEnrichmentService implements MovieEnrichmentService {

    private final CountryService countryService;
    private final GenreService genreService;
    private final ReviewService reviewService;
    private final ExecutorService executorService;

    @Value("${movieEnrichmentService.enrichmentTimeout}")
    private long enrichmentTimeout;

    @Override
    public Movie enrich(Movie movie) {
        Movie.MovieBuilder builder = new Movie.MovieBuilder().newMovie(movie);
        List<Runnable> tasks = Arrays.asList(
                () -> builder.setCountries(countryService.getAll(movie)),
                () -> builder.setGenres(genreService.getAll(movie)),
                () -> builder.setReviews(reviewService.getAll(movie)));

        List<Callable<Object>> callableList = tasks.stream().map(Executors::callable).collect(Collectors.toList());

        try {
            executorService.invokeAll(callableList, enrichmentTimeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            /*Restore the interrupted status*/
            Thread.currentThread().interrupt();
        }

        return builder.build();
    }

}
