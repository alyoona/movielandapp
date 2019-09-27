package com.stroganova.movielandapp.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.Role
import com.stroganova.movielandapp.entity.Session
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.service.ReviewService
import com.stroganova.movielandapp.service.SecurityService
import com.stroganova.movielandapp.web.handler.UserRequestAttributeArgumentResolver
import com.stroganova.movielandapp.web.interceptor.SecurityHandlerInterceptor
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDateTime

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


class ReviewControllerTest {
    private final ObjectMapper MAPPER = new ObjectMapper()
    @Mock
    private ReviewService reviewService

    private SecurityService securityService

    @InjectMocks
    private ReviewController reviewController
    private MockMvc mockMvc

    @Before
    void setup() {
        securityService = mock(SecurityService.class)
        SecurityHandlerInterceptor interceptor = new SecurityHandlerInterceptor(securityService)
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setCustomArgumentResolvers(new UserRequestAttributeArgumentResolver())
                .addInterceptors(interceptor).build()
    }


    @Test
    void testAddMovieReview() {

        String token = UUID.randomUUID().toString()
        def user = new User(id: 55L, role: Role.USER_ROLE)
        Optional<Session> sessionOptional = Optional.of(new Session(token, user, LocalDateTime.now()))
        when(securityService.getAuthorization(token)).thenReturn(sessionOptional)

        def requestBody = [movieId: 1L,
                           text   : "Очень понравилось!"]
        String requestBodyJson = MAPPER.writeValueAsString(requestBody)

        def response = mockMvc.perform(post("/review").header("Token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson)
        ).andReturn().response

        assert response.status == HttpStatus.CREATED.value()
        verify(reviewService).add(new Review(text: "Очень понравилось!", movie: new Movie(id: 1L), user: user))
    }
}
