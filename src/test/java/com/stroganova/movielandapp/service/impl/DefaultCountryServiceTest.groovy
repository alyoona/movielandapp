package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.CountryDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.service.CountryService
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DefaultCountryServiceTest {

    private CountryService countryService
    private CountryDao countryDao

    @Before
    void before(){
        countryDao = mock(CountryDao.class)
        countryService = new DefaultCountryService(countryDao)
    }

    @Test
    void testGetAllByMovie() {
        def countries = [new Country(id: 10L, name: "USA"), new Country(id: 20, name: "GB")]
        when(countryDao.getAll(new Movie(id: 1L))).thenReturn(countries)
        assert countries == countryService.getAll(new Movie(id: 1L))
    }
}
