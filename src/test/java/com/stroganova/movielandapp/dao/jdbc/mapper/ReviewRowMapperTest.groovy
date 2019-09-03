package com.stroganova.movielandapp.dao.jdbc.mapper

import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.User
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class ReviewRowMapperTest {
    @Test
    void testMapRow() {
        def user = new User(id: 1, nickname: "FirstName LastName")
        def expectedReview = new Review(id: 1, text: "Description!", user: user)

        def resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(expectedReview.getId())
        when(resultSet.getString("description")).thenReturn(expectedReview.getText())
        when(resultSet.getLong("user_id")).thenReturn(user.getId())
        when(resultSet.getString("first_name")).thenReturn("FirstName")
        when(resultSet.getString("last_name")).thenReturn("LastName")

        def reviewRowMapper = new ReviewRowMapper()
        def actualReview = reviewRowMapper.mapRow(resultSet,0)

        assertEquals(expectedReview.getId(), actualReview.getId())
        assertEquals(expectedReview.getText(), actualReview.getText())
        assertEquals(expectedReview.getUser().getId(), actualReview.getUser().getId())
        assertEquals(expectedReview.getUser().getNickname(), actualReview.getUser().getNickname())
    }
}
