package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.service.GenreService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class DefaultGenreService implements GenreService {

    @NonNull
    GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        log.info("Get all genres");
        return genreDao.getAll();
    }
}
