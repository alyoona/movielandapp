package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.enums.MovieRequestParam;
import com.stroganova.movielandapp.enums.SortingOrder;
import com.stroganova.movielandapp.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceDefault implements MovieService {

    private final MovieDao movieDao;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public MovieServiceDefault(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        LOGGER.info("Get all movies ");
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getAll(Map<String, String> allParams) {
        LOGGER.info("Get all movies and sort");
        return sort(movieDao.getAll(), allParams);
    }

    @Override
    public List<Movie> getAll(long genreId) {
        LOGGER.info("Get all movies by genre id ");
        return movieDao.getAll(genreId);
    }

    @Override
    public List<Movie> getAll(long genreId, Map<String, String> allParams) {
        LOGGER.info("Get all movies by genre id and sort");
        return sort(movieDao.getAll(genreId), allParams);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        Random random = new Random();
        List<Movie> allMovies = movieDao.getAll();
        LOGGER.info("Return 3 random movies from all movies");
        return random.ints(3, 0, allMovies.size())
                .mapToObj(allMovies::get)
                .collect(Collectors.toList());
    }


    private List<Movie> sort(List<Movie> movies, Map<String, String> allParams) {
        for (String paramName : allParams.keySet()) {

            MovieRequestParam movieRequestParam = MovieRequestParam.getByName(paramName);
            SortingOrder value = SortingOrder.getByName(allParams.get(paramName));

            if (MovieRequestParam.RATING.equals(movieRequestParam)) {
                if (SortingOrder.ASC.equals(value)) {
                    throw new IllegalArgumentException("Sorting by rating(acs) is not supported");
                }
                movies.sort(Comparator.comparingDouble(Movie::getRating));
                LOGGER.info("Sort movies by rating=desc");
                Collections.reverse(movies);
            } else if (MovieRequestParam.PRICE.equals(movieRequestParam)) {
                LOGGER.info("Sort movies by price=asc");
                movies.sort(Comparator.comparingDouble(Movie::getPrice));
                if (SortingOrder.DESC.equals(value)) {
                    LOGGER.info("Sort movies by price=desc");
                    Collections.reverse(movies);
                }
            }
        }
        return movies;
    }
}
