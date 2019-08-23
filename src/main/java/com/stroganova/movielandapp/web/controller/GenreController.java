package com.stroganova.movielandapp.web.controller;


import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.service.GenreService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class GenreController {

    @NonNull GenreService genreService;

    @GetMapping
    public List<Genre> getAll(){
        log.info("Get all genres");
        return genreService.getAll();
    }

}
