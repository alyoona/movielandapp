package com.stroganova.movielandapp.dao.cache;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Primary
public class CachedGenreDao implements GenreDao {

    @NonNull
    @Qualifier("jdbcGenreDao")
    final GenreDao genreDao;

    volatile List<Genre> genres;

    @Override
    public List<Genre> getAll() {
        log.info("Get all genres from cache");
        return new ArrayList<>(genres);
    }

    @Scheduled(fixedRateString = "${genresCache.refreshRate}", initialDelayString = "${genresCache.refreshRate}")
    @PostConstruct
    private void invalidate() {
        log.info("Update genres cache");
        genres = genreDao.getAll();
    }
}
