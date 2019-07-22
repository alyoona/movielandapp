package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class MovieController {


    @NonNull MovieService movieService;

    @GetMapping
    public List<Movie> getAll() {
        log.info("Get all movies ");
        return movieService.getAll();
    }

    @GetMapping("/genre/{genreId}")
    public List<Movie> getAll(@PathVariable long genreId) {
        log.info("Get all movies by genre id");
        return movieService.getAll(genreId);
    }

    @GetMapping("/random")
    public List<Movie> getThreeRandomMovies(){
        log.info("Get three random movies");
        return movieService.getThreeRandomMovies();
    }
}
