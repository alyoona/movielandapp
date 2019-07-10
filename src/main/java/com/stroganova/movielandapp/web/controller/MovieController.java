package com.stroganova.movielandapp.web.controller;



import com.stroganova.movielandapp.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        LOGGER.info("Get all movies");
        return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<?> getAll(@PathVariable long genreId) {
        LOGGER.info("Get all movies by genre id");
        return new ResponseEntity<>(movieService.getAll(genreId), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getThreeRandomMovies(){
        LOGGER.info("Get 3 random movies.");
        return new ResponseEntity<>(movieService.getThreeRandomMovies(), HttpStatus.OK);
    }

}
