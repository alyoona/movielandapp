package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.dao.ReviewDao
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.service.ReviewService
import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.mockito.Mockito.verify


class DefaultReviewServiceTest {

    private ReviewService reviewService
    private ReviewDao reviewDao

    @Before
    void before() {
        reviewDao = mock(ReviewDao.class)
        reviewService = new DefaultReviewService(reviewDao)
    }

    @Test
    void testGetAllByMovie() {
        def reviews = [new Review(id: 1000, text: "Great!", user: new User(id: 50, nickname: "Big Ben"))]
        when(reviewDao.getAll(new Movie(id: 2L))).thenReturn(reviews)
        assert reviews == reviewService.getAll(new Movie(id: 2L))
    }

    @Test
    void testAdd() {
        def review = new Review(user: new User(id: 12L), movie: new Movie(id: 27L), text: "Great!!!")
        reviewService.add(review)
        verify(reviewDao).add(review)
    }
}
