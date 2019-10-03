package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.CountryDao;
import com.stroganova.movielandapp.entity.Country;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.service.CountryService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultCountryService implements CountryService {

    @NonNull CountryDao countryDao;

    @Override
    public List<Country> getAll(Movie movie) {
        return countryDao.getAll(movie);
    }

    @Override
    public void link(long movieId, List<Country> countries) {
        countryDao.link(movieId, countries);
    }

    @Override
    @Transactional
    public void updateLinks(long movieId, List<Country> countries) {
        if (countries != null) {
            countryDao.deleteAllLinks(movieId);
            if (!countries.isEmpty()) {
                countryDao.link(movieId, countries);
            }
        }
    }
}
