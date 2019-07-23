package com.stroganova.movielandapp.dao.jdbc;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CachedJdbcGenreDao implements GenreDao {

    @NonNull
    @Qualifier("jdbcGenreDao")
    final GenreDao genreDao;

    List<Genre> genres;

    @Override
    synchronized public List<Genre> getAll() {
        log.info("Get all genres from cache");
            return new ArrayList<>(genres);
    }

    @Scheduled(fixedRateString = "${genresCache.updatingIntervalMilliseconds.everyFourHours}")
    synchronized public void updateGenres(){
        log.info("Update genres cache from DB");
            genres = genreDao.getAll();
        if(genres.size() == 0) {
            log.warn("Genres cache is empty, there are not genres in DB");
        }
    }
}
