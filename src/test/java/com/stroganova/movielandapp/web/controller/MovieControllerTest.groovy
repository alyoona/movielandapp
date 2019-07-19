package com.stroganova.movielandapp.web.controller

import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.service.MovieService
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.mockito.Mockito.when


import java.time.LocalDate


class MovieControllerTest {

    @Mock
    private MovieService movieService
    @InjectMocks
    private MovieController movieController
    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build()
    }

    @Test
    void testGetAll() {
        def movieFirst = new Movie(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        )
        def movieSecond = new Movie(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        )

        when(movieService.getAll()).thenReturn([movieFirst, movieSecond])

        def response = mockMvc.perform(get("/movie")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def content = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovies = [movieFirst, movieSecond]

        content.eachWithIndex { actualMovie, index ->
            assert expectedMovies[index], actualMovie
            assert expectedMovies[index].id, actualMovie.id
        }
    }
}
