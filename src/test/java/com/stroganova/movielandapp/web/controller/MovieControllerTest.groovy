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

import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.junit.Assert.assertEquals
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
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussianFirst")
        movieFirst.setNameNative("NameNativeFirst")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussianSecond")
        movieSecond.setNameNative("NameNativeSecond")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def expectedMovies = [movieFirst, movieSecond]

        when(movieService.getAll()).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            assertEquals(expectedMovies[index].nameRussian, actualMovie.nameRussian)
            assertEquals(expectedMovies[index].nameNative, actualMovie.nameNative)
            assertEquals(expectedMovies[index].yearOfRelease.format(DateTimeFormatter.ofPattern("yyyy")), actualMovie.yearOfRelease)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].rating).replace(",", "."), actualMovie.rating)
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
            assertEquals(expectedMovies[index].picturePath, actualMovie.picturePath)
        }
    }

    @Test
    void testGetAllAndSortByRatingDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setRating(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setRating(8D)

        def expectedMovies = [movieFirst, movieSecond]

        def params = ["rating": "desc"]

        when(movieService.getAll(params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie?rating=desc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].rating).replace(",", "."), actualMovie.rating)
        }
    }

    @Test
    void testGetAllAndSortByPriceDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8D)

        def expectedMovies = [movieFirst, movieSecond]

        def params = ["price": "desc"]

        when(movieService.getAll(params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie?price=desc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
        }
    }

    @Test
    void testGetAllAndSortByPriceAsc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8D)

        def expectedMovies = [movieSecond, movieFirst]

        def params = ["price": "asc"]

        when(movieService.getAll(params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie?price=asc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
        }
    }

    @Test
    void testGetAllByGenreId() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussianFirst")
        movieFirst.setNameNative("NameNativeFirst")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussianSecond")
        movieSecond.setNameNative("NameNativeSecond")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def expectedMovies = [movieFirst, movieSecond]

        when(movieService.getAll(1L)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie/genre/1")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            assertEquals(expectedMovies[index].nameRussian, actualMovie.nameRussian)
            assertEquals(expectedMovies[index].nameNative, actualMovie.nameNative)
            assertEquals(expectedMovies[index].yearOfRelease.format(DateTimeFormatter.ofPattern("yyyy")), actualMovie.yearOfRelease)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].rating).replace(",", "."), actualMovie.rating)
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
            assertEquals(expectedMovies[index].picturePath, actualMovie.picturePath)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByRatingDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setRating(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setRating(8D)

        def expectedMovies = [movieFirst, movieSecond]

        def params = ["rating": "desc"]

        when(movieService.getAll(1L, params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie/genre/1?rating=desc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].rating).replace(",", "."), actualMovie.rating)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByPriceDesc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8D)

        def expectedMovies = [movieFirst, movieSecond]

        def params = ["price": "desc"]

        when(movieService.getAll(1L, params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie/genre/1?price=desc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
        }
    }

    @Test
    void testGetAllByGenreIdAndSortByPriceAsc() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setPrice(8.99D)
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setPrice(8D)

        def expectedMovies = [movieSecond, movieFirst]

        def params = ["price": "asc"]

        when(movieService.getAll(1L, params)).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie/genre/1?price=asc")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        println(response.contentAsString)

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            println(actualMovie)
            assertEquals(expectedMovies[index].id, actualMovie.id)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
        }
    }

    @Test
    void testGetThreeRandomMovies() {
        def movieFirst = new Movie()
        movieFirst.setId(1L)
        movieFirst.setNameRussian("NameRussianFirst")
        movieFirst.setNameNative("NameNativeFirst")
        movieFirst.setYearOfRelease(LocalDate.of(1994, 1, 1))
        movieFirst.setRating(8.99D)
        movieFirst.setPrice(150.15D)
        movieFirst.setPicturePath("https://picture_path.png")
        def movieSecond = new Movie()
        movieSecond.setId(2L)
        movieSecond.setNameRussian("NameRussianSecond")
        movieSecond.setNameNative("NameNativeSecond")
        movieSecond.setYearOfRelease(LocalDate.of(1996, 1, 1))
        movieSecond.setRating(8D)
        movieSecond.setPrice(150D)
        movieSecond.setPicturePath("https://picture_path.png")

        def expectedMovies = [movieFirst, movieSecond, movieFirst]

        when(movieService.getThreeRandomMovies()).thenReturn(expectedMovies)

        def response = mockMvc.perform(get("/movie/random")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def content = new JsonSlurper().parseText(response.contentAsString)
        content.eachWithIndex { actualMovie, index ->
            assertEquals(expectedMovies[index].id, actualMovie.id)
            assertEquals(expectedMovies[index].nameRussian, actualMovie.nameRussian)
            assertEquals(expectedMovies[index].nameNative, actualMovie.nameNative)
            assertEquals(expectedMovies[index].yearOfRelease.format(DateTimeFormatter.ofPattern("yyyy")), actualMovie.yearOfRelease)
            DecimalFormat decimalFormat = new DecimalFormat("0.00")
            assertEquals(decimalFormat.format(expectedMovies[index].rating).replace(",", "."), actualMovie.rating)
            assertEquals(decimalFormat.format(expectedMovies[index].price).replace(",", "."), actualMovie.price)
            assertEquals(expectedMovies[index].picturePath, actualMovie.picturePath)
        }
    }

}
