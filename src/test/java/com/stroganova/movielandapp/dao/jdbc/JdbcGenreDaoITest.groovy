package com.stroganova.movielandapp.dao.jdbc

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
    NamedParameterJdbcTemplate namedParameterJdbcTemplate

    @Autowired
    JdbcGenreDao genreDao

    def genreInsertSql = "INSERT INTO movieland.genre (id, name) VALUES (:id, :name);"

    @Before
    void clear() {
        def genreDeleteSql = "DELETE FROM movieland.genre;"
        namedParameterJdbcTemplate.update(genreDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testGetAll() {
        Map<String, ?>[] genreBatchValues = [[id: 1L, name: "genreFirst"],
                                             [id: 2L, name: "genreSecond"]]

        namedParameterJdbcTemplate.batchUpdate(genreInsertSql, genreBatchValues)

        def expectedGenres = [new Genre(id: 1L, name: "genreFirst"),
                              new Genre(id: 2L, name: "genreSecond")]

        def actualGenres = genreDao.getAll()

        assert expectedGenres == actualGenres
    }

}
