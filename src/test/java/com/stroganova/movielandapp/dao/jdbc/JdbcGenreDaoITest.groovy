package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.GenreDao
import com.stroganova.movielandapp.entity.Genre
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
class JdbcGenreDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate

    @Autowired
    GenreDao genreDao

    def genreInsertSql = "INSERT INTO movieland.genre (id, name) VALUES (:id, :name);"
    def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
            " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"
    def movieGenreInsertSql = "INSERT INTO movieland.movie_genre (id, movie_id, genre_id) VALUES (:id, :movie_id, :genre_id);"

    @Before
    void clear() {
        def movieGenreDeleteSql = "DELETE FROM movieland.movie_genre;"
        def genreDeleteSql = "DELETE FROM movieland.genre;"

        namedJdbcTemplate.update(movieGenreDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(genreDeleteSql, EmptySqlParameterSource.INSTANCE)

    }

    @Test
    void testGetAll() {

        Map<String, ?>[] genreBatchValues = [[id: 1L, name: "genreFirst"],
                                             [id: 2L, name: "genreSecond"]]

        namedJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)

        def expectedGenres = [new Genre(1L, "genreFirst"),
                              new Genre(2L, "genreSecond")]

        def actualGenres = genreDao.getAll()

        assert expectedGenres == actualGenres
    }

    @Test
    void testGetAllByMovieId(){
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 3L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        //genres
        Map<String, ?>[] genreBatchValues = [[id: 100L, name: "genreFirst"],
                                             [id: 200L, name: "genreSecond"]]
        Map<String, ?>[] movieGenreBatchValues = [[id: 1L, movie_id: 3L, genre_id: 100L],
                                                  [id: 2L, movie_id: 3L, genre_id: 200L]]
        namedJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)
        namedJdbcTemplate.batchUpdate(movieGenreInsertSql, movieGenreBatchValues)

        def genres = [new Genre(100L, "genreFirst"), new Genre(200L, "genreSecond")]

        def actualGenres = genreDao.getAll(3L)
        assert genres == actualGenres
    }

}
