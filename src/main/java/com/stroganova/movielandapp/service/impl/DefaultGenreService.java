package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.GenreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultGenreService implements GenreService {

    GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        log.info("Get all genres");
        return genreDao.getAll();
    }

    @Override
    public List<Genre> getAll(Movie movie) {
        return genreDao.getAll(movie);
    }

    @Override
    public void link(long movieId, List<Genre> genres) {
        genreDao.link(movieId, genres);
    }

    @Override
    @Transactional
    public void updateLinks(long movieId, List<Genre> genres) {
        if (genres != null) {
            genreDao.deleteAllLinks(movieId);
            if (!genres.isEmpty()) {
                genreDao.link(movieId, genres);
            }
        }
    }
}
