package com.stroganova.movielandapp.dao;

import com.stroganova.movielandapp.entity.Country;

import java.util.List;

public interface CountryDao {

    List<Country> getAll(long movieId);
}
