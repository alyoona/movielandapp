package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.GenreDao;
import com.stroganova.movielandapp.entity.Genre;
import com.stroganova.movielandapp.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceDefault implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceDefault(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
