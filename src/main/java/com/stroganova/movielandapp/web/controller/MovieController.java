package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.MovieService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class MovieController {
    @NonNull MovieService movieService;

    @GetMapping("/movie")
    public List<Movie> getAll() {
        return movieService.getAll();
    }
}
