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
    NamedParameterJdbcTemplate namedJdbcTemplate

    @Autowired
    JdbcGenreDao genreDao

    def genreInsertSql = "INSERT INTO movieland.genre (id, name) VALUES (:id, :name);"

    @Before
    void clear() {
        def genreDeleteSql = "DELETE FROM movieland.genre;"
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

}
