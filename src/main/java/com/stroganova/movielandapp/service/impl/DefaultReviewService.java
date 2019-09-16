package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.ReviewDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.service.ReviewService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class DefaultReviewService implements ReviewService {

    @NonNull ReviewDao reviewDao;

    @Override
    public List<Review> getAll(Movie movie) {
        return reviewDao.getAll(movie);
    }
}
