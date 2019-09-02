package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.RequestParameter;
import com.stroganova.movielandapp.service.MovieService;
import com.stroganova.movielandapp.request.SortDirection;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MovieController {

    @NonNull MovieService movieService;

    @GetMapping
    public List<Movie> getAll(RequestParameter requestParameter) {
        log.info("Get all movies");
        return requestParameter != null ? movieService.getAll(requestParameter) : movieService.getAll();
    }

    @GetMapping("/genre/{genreId}")
    public List<Movie> getAll(@PathVariable long genreId, RequestParameter requestParameter) {
        log.info("Get all movies by genre id");
        return requestParameter != null ? movieService.getAll(genreId, requestParameter) : movieService.getAll(genreId);
    }

    @GetMapping("/random")
    public List<Movie> getThreeRandomMovies() {
        log.info("Get three random movies");
        return movieService.getThreeRandomMovies();
    }


}
