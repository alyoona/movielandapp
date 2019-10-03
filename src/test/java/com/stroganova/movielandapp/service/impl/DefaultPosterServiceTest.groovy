package com.stroganova.movielandapp.service.impl


import com.stroganova.movielandapp.dao.PosterDao
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
        long movieId = 26L
        posterService.update(movieId, "https://picture_path.png")
        verify(posterDao).update(movieId, "https://picture_path.png")
    }

    @Test
    void testAdd() {
        long movieId = 22L
        String picturePath = "picturePath"
        posterService.link(movieId, picturePath)

        verify(posterDao).link(movieId, picturePath)

    }
}
