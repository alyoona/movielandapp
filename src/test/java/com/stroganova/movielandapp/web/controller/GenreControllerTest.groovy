package com.stroganova.movielandapp.web.controller

import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.service.GenreService
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class GenreControllerTest {

    @Mock
    private GenreService genreService
    @InjectMocks
    private GenreController genreController
    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build()
    }

    @Test
    void testGetAll() {
        def expectedGenres = [new Genre(1L, "genreFirstName"),
                              new Genre(1L, "genreFirstName")]

        when(genreService.getAll()).thenReturn(expectedGenres)

        def response = mockMvc.perform(get("/genre")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def actualGenres = new JsonSlurper().parseText(response.contentAsString)

        actualGenres.eachWithIndex{ actualGenre, index ->
            assert expectedGenres[index].id == actualGenre.id
            assert expectedGenres[index].name == actualGenre.name
        }
    }
}
