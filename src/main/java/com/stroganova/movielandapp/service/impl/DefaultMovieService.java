package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.exception.EntityNotFoundException;
import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.request.MovieUpdateDirections;
import com.stroganova.movielandapp.request.RequestParameter;
import com.stroganova.movielandapp.service.*;
import com.stroganova.movielandapp.service.cache.MovieCache;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultMovieService implements MovieService {

    @NonNull MovieDao movieDao;
    @NonNull CountryService countryService;
    @NonNull GenreService genreService;
    @NonNull ReviewService reviewService;
    @NonNull CurrencyService currencyService;
    @NonNull PosterService posterService;
    @NonNull MovieCache movieCache;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @Value("${movieService.enrichmentTimeout}")
    private long enrichmentTimeout;

    @Override
    public List<Movie> getAll() {
        log.info("Get all movies");
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getAll(long genreId) {
        log.info("Get all movies by genre id");
        return movieDao.getAll(genreId);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        log.info("Get three random movies");
        return movieDao.getThreeRandomMovies();
    }

    @Override
    public List<Movie> getAll(RequestParameter requestParameter) {
        return movieDao.getAll(requestParameter);
    }

    @Override
    public List<Movie> getAll(long genreId, RequestParameter requestParameter) {
        log.info("Get all movies by genre id");
        return movieDao.getAll(genreId, requestParameter);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getById(long movieId) {

        Movie cachedMovie = movieCache.getById(movieId);
        if (cachedMovie != null) {
            return cachedMovie;
        }

        Movie movie = movieDao.getById(movieId);
        if (movie == null) {
            throw new EntityNotFoundException("No such movie");
        }

        Movie enrichedMovie = enrich(movie);
        if (enrichedMovie != null) {
            movieCache.cacheMovie(enrichedMovie);
            return enrichedMovie;
        }

        movieCache.cacheMovie(movie);
        return movie;
    }

    private Movie enrich(Movie movie) {
        Future<List<Country>> futureCountries = executorService.submit(() -> countryService.getAll(movie));
        Future<List<Genre>> futureGenres = executorService.submit(() -> genreService.getAll(movie));
        Future<List<Review>> futureReviews = executorService.submit(() -> reviewService.getAll(movie));
        List<Country> countries;
        List<Genre> genres;
        List<Review> reviews;
        try {
            try {
                countries = futureCountries.get(enrichmentTimeout, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                futureCountries.cancel(true);
                return null;
            }
            try {
                genres = futureGenres.get(enrichmentTimeout, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                futureGenres.cancel(true);
                return null;
            }
            try {
                reviews = futureReviews.get(enrichmentTimeout, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                futureReviews.cancel(true);
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        movie.setCountries(countries);
        movie.setGenres(genres);
        movie.setReviews(reviews);
        return movie;
    }

    @Override
    public Movie getById(long movieId, RequestParameter requestParameter) {
        Movie movie = getById(movieId);
        Currency currency = requestParameter.getCurrency();
        if (currency != null) {
            double convertedPrice = currencyService.convert(movie.getPrice(), currency);
            movie.setPrice(convertedPrice);
        }
        return movie;
    }

    @Override
    @Transactional
    public Movie add(Movie movie) {
        long movieId = movieDao.add(movie);
        posterService.link(movieId, movie.getPicturePath());
        countryService.link(movieId, movie.getCountries());
        genreService.link(movieId, movie.getGenres());
        return getById(movieId);
    }


    @Override
    @Transactional
    public Movie partialUpdate(long movieId, MovieUpdateDirections updates) {
        movieCache.invalidateCachedMovie(movieId);

        movieDao.partialUpdate(movieId, updates.getMovieUpdates());
        posterService.update(movieId, updates.getPoster());
        countryService.updateLinks(movieId, updates.getCountries());
        genreService.updateLinks(movieId, updates.getGenres());
        return getById(movieId);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {
        movieCache.invalidateCachedMovie(movie.getId());

        movieDao.update(movie);
        posterService.update(movie.getId(), movie.getPicturePath());
        countryService.updateLinks(movie.getId(), movie.getCountries());
        genreService.updateLinks(movie.getId(), movie.getGenres());
        return getById(movie.getId());
    }

}
