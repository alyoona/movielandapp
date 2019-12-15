package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.request.MovieRequestParameterList;
import com.stroganova.movielandapp.request.MovieUpdateDirections;
import com.stroganova.movielandapp.service.*;
import com.stroganova.movielandapp.service.cache.MovieCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieDao movieDao;
    private final CountryService countryService;
    private final GenreService genreService;
    private final CurrencyService currencyService;
    private final PosterService posterService;
    private final MovieCache movieCache;


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
    public List<Movie> getAll(MovieRequestParameterList movieRequestParameterList) {
        return movieDao.getAll(movieRequestParameterList);
    }

    @Override
    public List<Movie> getAll(long genreId, MovieRequestParameterList movieRequestParameterList) {
        log.info("Get all movies by genre id");
        return movieDao.getAll(genreId, movieRequestParameterList);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getById(long movieId) {
        return movieCache.getById(movieId);
    }


    @Override
    public Movie getById(long movieId, MovieRequestParameterList movieRequestParameterList) {
        Movie movie = getById(movieId);
        Currency currency = movieRequestParameterList.getCurrency();
        if (currency != null) {
            double convertedPrice = currencyService.convert(movie.getPrice(), currency);
            return new Movie.MovieBuilder()
                    .newMovie(movie)
                    .setPrice(convertedPrice)
                    .build();
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

        movieCache.invalidateCachedMovie(movieId);

        return getById(movieId);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {
        movieDao.update(movie);
        posterService.update(movie.getId(), movie.getPicturePath());
        countryService.updateLinks(movie.getId(), movie.getCountries());
        genreService.updateLinks(movie.getId(), movie.getGenres());

        movieCache.invalidateCachedMovie(movie.getId());

        return getById(movie.getId());
    }

}
