package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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
    public List<Movie> getAll(long genreId) {
        LOGGER.info("Get all movies by genre id ");
        return movieDao.getAll(genreId);
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
}
