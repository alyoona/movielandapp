package com.stroganova.movielandapp.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Role;
import com.stroganova.movielandapp.request.MovieUpdateDirections;
import com.stroganova.movielandapp.request.MovieRequestParameterList;
import com.stroganova.movielandapp.service.MovieService;
import com.stroganova.movielandapp.views.MovieView;

import com.stroganova.movielandapp.web.annotation.Secured;
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

    @JsonView(MovieView.Summary.class)
    @GetMapping
    public List<Movie> getAll(MovieRequestParameterList movieRequestParameterList) {
        log.info("Get all movies");
        return movieRequestParameterList != null ? movieService.getAll(movieRequestParameterList) : movieService.getAll();
    }

    @JsonView(MovieView.Summary.class)
    @GetMapping("/genre/{genreId}")
    public List<Movie> getAll(@PathVariable long genreId, MovieRequestParameterList movieRequestParameterList) {
        log.info("Get all movies by genre id");
        return movieRequestParameterList != null ? movieService.getAll(genreId, movieRequestParameterList) : movieService.getAll(genreId);
    }

    @JsonView(MovieView.Summary.class)
    @GetMapping("/random")
    public List<Movie> getThreeRandomMovies() {
        log.info("Get three random movies");
        return movieService.getThreeRandomMovies();
    }

    @JsonView(MovieView.MovieDetail.class)
    @GetMapping("/{movieId}")
    public Movie getById(@PathVariable long movieId, MovieRequestParameterList movieRequestParameterList) {
        log.info("Get movie details");
        return movieRequestParameterList != null ? movieService.getById(movieId, movieRequestParameterList) : movieService.getById(movieId);
    }

    @PostMapping
    @Secured(role = Role.ADMIN)
    @JsonView(MovieView.MovieDetail.class)
    public Movie add(@RequestBody Movie movie) {
        return movieService.add(movie);
    }

    @PatchMapping("/{id}")
    @Secured(role = Role.ADMIN)
    @JsonView(MovieView.MovieDetail.class)
    public Movie partialUpdate(@PathVariable long id, @RequestBody MovieUpdateDirections updates) {
        return movieService.partialUpdate(id, updates);

    }

    @PutMapping
    @Secured(role = Role.ADMIN)
    @JsonView(MovieView.MovieDetail.class)
    public Movie update(@RequestBody Movie movie) {
        return movieService.update(movie);
    }

}
