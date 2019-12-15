package com.stroganova.movielandapp.service.enrichment.impl;

import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.service.CountryService;
import com.stroganova.movielandapp.service.GenreService;
import com.stroganova.movielandapp.service.enrichment.MovieEnrichmentService;
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

    @Value("${movieEnrichmentService.enrichmentTimeout.seconds:5}")
    private long enrichmentTimeout;

    @Override
    public Movie enrich(Movie movie) {
        Movie.MovieBuilder builder = new Movie.MovieBuilder().newMovie(movie);
        List<Runnable> tasks = Arrays.asList(
                () -> {
                    List<Country> countries = countryService.getAll(movie);
                    if (!Thread.currentThread().isInterrupted()) {
                        builder.setCountries(countries);
                    }
                },
                () -> {
                    List<Genre> genres = genreService.getAll(movie);
                    if (!Thread.currentThread().isInterrupted()) {
                        builder.setGenres(genres);
                    }
                },
                () -> {
                    List<Review> reviews = reviewService.getAll(movie);
                    if (!Thread.currentThread().isInterrupted()) {
                        builder.setReviews(reviews);
                    }
                });

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
