package com.stroganova.movielandapp.dao;

public interface PosterDao {

    void add(long movieId, String picturePath);

    void update(long movieId, String updates);
}
