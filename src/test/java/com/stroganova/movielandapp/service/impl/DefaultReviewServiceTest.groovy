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
        def reviews = [new Review.ReviewBuilder(
                id: 1000,
                text: "Great!",
                user: new User.UserBuilder(id: 50, nickname: "Big Ben").build()
        ).build()]
        when(reviewDao.getAll(new Movie.MovieBuilder(id: 2L).build())).thenReturn(reviews)
        assert reviews == reviewService.getAll(new Movie.MovieBuilder(id: 2L).build())
    }

    @Test
    void testAdd() {
        def review = new Review.ReviewBuilder(
                user: new User.UserBuilder(id: 12L).build(),
                movie: new Movie.MovieBuilder(id: 27L).build(),
                text: "Great!!!"
        ).build()
        reviewService.add(review)
        verify(reviewDao).add(review)
    }
}
