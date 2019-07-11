package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceDefault implements GenreService {

    private final GenreDao genreDao;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public GenreServiceDefault(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Cacheable(cacheNames="genres")
    @Override
    public List<Genre> getAll() {
        LOGGER.info("Get all genres.");
        return genreDao.getAll();
    }
}
