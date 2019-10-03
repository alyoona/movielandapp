package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.exception.EntityNotFoundException;
import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.request.MovieUpdateDirections;
import com.stroganova.movielandapp.request.RequestParameter;
import com.stroganova.movielandapp.service.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Movie movie = movieDao.getById(movieId);
        if (movie == null) {
            throw new EntityNotFoundException("No such movie");
        }
        return enrich(movie);
    }

    private Movie enrich(Movie movie) {
        movie.setCountries(countryService.getAll(movie));
        movie.setGenres(genreService.getAll(movie));
        movie.setReviews(reviewService.getAll(movie));
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
        movieDao.partialUpdate(movieId, updates.getMovieUpdates());
        posterService.update(movieId, updates.getPoster());
        countryService.updateLinks(movieId, updates.getCountries());
        genreService.updateLinks(movieId, updates.getGenres());
        return getById(movieId);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {
        movieDao.update(movie);
        posterService.update(movie.getId(), movie.getPicturePath());
        countryService.updateLinks(movie.getId(), movie.getCountries());
        genreService.updateLinks(movie.getId(), movie.getGenres());
        return getById(movie.getId());
    }

}
