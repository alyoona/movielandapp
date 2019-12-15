package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.entity.Review;
import com.stroganova.movielandapp.entity.Role;
import com.stroganova.movielandapp.service.ReviewService;
import com.stroganova.movielandapp.web.annotation.Secured;
import com.stroganova.movielandapp.web.handler.UserHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReviewController {

    ReviewService reviewService;

    @PostMapping
    @Secured(role = Role.USER)
    public ResponseEntity<String> addMovieReview(@RequestBody Review review) {

        Review newReview = new Review.ReviewBuilder()
                .newReview(review)
                .setUser(UserHolder.getLoggedUser())
                .build();
        reviewService.add(newReview);
        return new ResponseEntity<>("Added movie review", HttpStatus.CREATED);
    }
}
