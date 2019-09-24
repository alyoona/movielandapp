package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.request.MovieUpdateDirections;

public interface PosterService {

    void add(long movieId, String picturePath);

    void update(long movieId, MovieUpdateDirections updates);
}
