package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll(Movie movie);

    void add(Review review);
}
