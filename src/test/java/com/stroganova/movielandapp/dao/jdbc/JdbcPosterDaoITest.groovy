package com.stroganova.movielandapp.dao.jdbc

import com.stroganova.movielandapp.dao.PosterDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/rootContextTest.xml")

class JdbcPosterDaoITest {

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate
    @Autowired
    PosterDao posterDao

    def movieInsertSql = "INSERT INTO movieland.movie (id, name_russian, name_native, year, description, rating, price)" +
            " VALUES (:id, :name_russian, :name_native, :year, :description, :rating, :price)"
    def selectPicturePathPosterSql = "SELECT p.picture_path FROM movieland.poster p WHERE p.movie_id = :movieId"

    @Before
    void clear() {
        def movieDeleteSql = "DELETE FROM movieland.movie;"
        def posterDeleteSql = "DELETE FROM movieland.poster;"
        namedJdbcTemplate.update(movieDeleteSql, EmptySqlParameterSource.INSTANCE)
        namedJdbcTemplate.update(posterDeleteSql, EmptySqlParameterSource.INSTANCE)
    }

    @Test
    void testAdd() {
        //movie
        namedJdbcTemplate.update(movieInsertSql, [id          : 44L,
                                                  name_russian: "NameRussian",
                                                  name_native : "NameNative",
                                                  year        : "1995-01-01",
                                                  description : "empty",
                                                  rating      : 8.99D,
                                                  price       : 150.15D])
        long movieId = 44L
        String picturePath = "picturePath"
        posterDao.add(movieId, picturePath)

        String addedPicturePath = namedJdbcTemplate.queryForObject(selectPicturePathPosterSql,
                new MapSqlParameterSource("movieId", movieId), String.class)

        assert picturePath == addedPicturePath.toString()
    }
}