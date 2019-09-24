package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.CountryDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.MovieUpdateDirections
import com.stroganova.movielandapp.service.CountryService
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class DefaultCountryServiceTest {

    private CountryService countryService
    private CountryDao countryDao

    @Before
    void before() {
        countryDao = mock(CountryDao.class)
        countryService = new DefaultCountryService(countryDao)
    }

    @Test
    void testUpdate() {
        def movie = new Movie(countries: [new Country(id: 1), new Country(id: 2), new Country(id: 3)])
        Map<MovieFieldUpdate, Object> map = new HashMap<>()
        map.put(MovieFieldUpdate.COUNTRIES, MovieFieldUpdate.COUNTRIES.getValue(movie))
        long movieId = 26L
        def updates = new MovieUpdateDirections(map)
        countryService.update(movieId, updates)
        verify(countryDao).deleteAll(movieId)
        verify(countryDao).add(movieId, [new Country(id: 1), new Country(id: 2), new Country(id: 3)])
    }

    @Test
    void testAdd() {
        long movieId = 22L
        def countries = [new Country(id: 10L, name: "USA"), new Country(id: 20, name: "GB")]
        countryService.add(movieId, countries)
        verify(countryDao).add(movieId, countries)
    }

    @Test
    void testGetAllByMovie() {
        def countries = [new Country(id: 10L, name: "USA"), new Country(id: 20, name: "GB")]
        when(countryDao.getAll(new Movie(id: 1L))).thenReturn(countries)
        assert countries == countryService.getAll(new Movie(id: 1L))
    }
}
