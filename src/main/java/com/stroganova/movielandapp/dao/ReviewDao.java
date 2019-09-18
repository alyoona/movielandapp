package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getAll(Movie movie);

    void add(Review review);
}
