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

import static org.junit.Assert.assertEquals
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
        def genreFirst = new Genre()
        genreFirst.setId(1L)
        genreFirst.setName("genreFirstName")
        def genreSecond = new Genre()
        genreSecond.setId(2L)
        genreSecond.setName("genreSecondName")

        def expectedGenres = [genreFirst, genreSecond]

        when(genreService.getAll()).thenReturn(expectedGenres)

        def response = mockMvc.perform(get("/genre")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex{ actualGenre, index ->
            println(actualGenre)
            assertEquals(expectedGenres[index].id, actualGenre.id)
            assertEquals(expectedGenres[index].name, actualGenre.name)
        }
    }
}
