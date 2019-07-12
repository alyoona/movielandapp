package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.MovieDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private final MovieDao movieDao;

    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
