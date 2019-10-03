package com.stroganova.movielandapp.service;

public interface PosterService {

    void link(long movieId, String picturePath);

    void update(long movieId, String picturePath);
}
