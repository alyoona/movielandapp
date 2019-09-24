package com.stroganova.movielandapp.service.impl


import com.stroganova.movielandapp.dao.PosterDao
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.MovieUpdateDirections
import com.stroganova.movielandapp.service.PosterService
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class DefaultPosterServiceTest {

    private PosterService posterService
    private PosterDao posterDao

    @Before
    void before() {
        posterDao = mock(PosterDao.class)
        posterService = new DefaultPosterService(posterDao)
    }

    @Test
    void testUpdate() {
        def movie = new Movie(picturePath: "https://picture_path.png")
        Map<MovieFieldUpdate, Object> map = new HashMap<>()
        map.put(MovieFieldUpdate.PICTURE_PATH, MovieFieldUpdate.PICTURE_PATH.getValue(movie))
        def updates = new MovieUpdateDirections(map)
        long movieId = 26L
        posterService.update(movieId, updates)
        verify(posterDao).update(movieId, "https://picture_path.png")
    }

    @Test
    void testAdd() {
        long movieId = 22L
        String picturePath = "picturePath"
        posterService.add(movieId, picturePath)

        verify(posterDao).add(movieId, picturePath)

    }
}
