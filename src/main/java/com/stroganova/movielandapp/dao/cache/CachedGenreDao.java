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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Primary
public class CachedGenreDao implements GenreDao {

    @NonNull
    @Qualifier("jdbcGenreDao")
    final GenreDao genreDao;

    List<Genre> genres;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public List<Genre> getAll() {
        lock.readLock().lock();
        try {
            log.info("Get all genres from cache");
            return new ArrayList<>(genres);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Scheduled(fixedRateString = "${genresCache.refreshRate}", initialDelayString = "${genresCache.refreshRate}")
    @PostConstruct
    public void invalidate(){
        lock.writeLock().lock();
        try {
            log.info("Update genres cache from DB");
            genres = genreDao.getAll();
            if(genres.size() == 0) {
                log.warn("Genres cache is empty, there are not genres in DB");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
