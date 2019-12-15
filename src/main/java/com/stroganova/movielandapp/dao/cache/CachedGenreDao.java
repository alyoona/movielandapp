package com.stroganova.movielandapp.dao.cache;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Primary
public class CachedGenreDao implements GenreDao {

    private final GenreDao genreDao;
    private volatile List<Genre> genres;

    @Override
    public List<Genre> getAll() {
        log.info("Get all genres from cache");
        return new ArrayList<>(genres);
    }

    @Override
    public List<Genre> getAll(Movie movie) {
        log.info("Get all genres by movie");
        return genreDao.getAll(movie);
    }

    @Override
    public void link(long movieId, List<Genre> genres) {
        genreDao.link(movieId, genres);
    }

    @Override
    public void deleteAllLinks(long movieId) {
        genreDao.deleteAllLinks(movieId);
    }

    @Scheduled(fixedRateString = "${genresCache.refreshRate}", initialDelayString = "${genresCache.refreshRate}")
    @PostConstruct
    private void invalidate() {
        log.info("Update genres cache");
        genres = genreDao.getAll();
    }
}
