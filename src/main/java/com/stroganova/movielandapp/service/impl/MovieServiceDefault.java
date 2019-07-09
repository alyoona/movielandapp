package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MovieServiceDefault implements MovieService {

    private final MovieDao movieDao;

    public MovieServiceDefault(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        Random random = new Random();
        List<Movie> allMovies = movieDao.getAll();
        return random.ints(3, 0, allMovies.size())
                .mapToObj(allMovies::get)
                .collect(Collectors.toList());
    }
}
