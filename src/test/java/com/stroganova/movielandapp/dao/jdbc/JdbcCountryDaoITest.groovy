package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.CountryDao
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/rootContextTest.xml")

class JdbcCountryDaoITest {
    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    CountryDao countryDao

    def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
            " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"
    def countryInsertSql = "INSERT INTO movieland.country (id, name) VALUES (:id, :name);"
    def movieCountryInsertSql = "INSERT INTO movieland.movie_country (id, movie_id, country_id) VALUES (:id, :movie_id, :country_id);"
    @Before
    void clear() {
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def countryDeleteSql = "DELETE FROM movieland.country;"
        def movieCountryDeleteSql = "DELETE FROM movieland.movie_country;"
        namedJdbcTemplate.update(movieCountryDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(countryDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGetAllByMovieId(){
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 1L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        //countries
        Map<String, ?>[] countryBatchValues = [[id: 10L, name: "countryFirst"],
                                               [id: 20L, name: "countrySecond"]]
        Map<String, ?>[] movieCountryBatchValues = [[id: 1L, movie_id: 1L, country_id: 10L],
                                                    [id: 2L, movie_id: 1L, country_id: 20L]]

        namedJdbcTemplate.batchUpdate(countryInsertSql, countryBatchValues)
        namedJdbcTemplate.batchUpdate(movieCountryInsertSql, movieCountryBatchValues)

        def countries = [new Country(id: 10L, name: "countryFirst"), new Country(id: 20L, name: "countrySecond") ]

        def actualCountries = countryDao.getAll(new Movie(id: 1L))
        assert countries == actualCountries
    }
}
