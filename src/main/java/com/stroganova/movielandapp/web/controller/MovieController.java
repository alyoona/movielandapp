package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/v1/movie")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);
    }
}
