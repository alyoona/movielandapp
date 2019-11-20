package com.stroganova.movielandapp.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.stroganova.movielandapp.entity.Country
import com.stroganova.movielandapp.entity.Genre
import com.stroganova.movielandapp.entity.Movie
import com.stroganova.movielandapp.entity.Review
import com.stroganova.movielandapp.entity.Role
import com.stroganova.movielandapp.entity.Session
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.request.Currency
import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.MovieUpdateDirections
import com.stroganova.movielandapp.request.RequestParameter
import com.stroganova.movielandapp.service.MovieService
import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
import com.stroganova.movielandapp.service.SecurityService
import com.stroganova.movielandapp.web.handler.RequestParameterArgumentResolver
import com.stroganova.movielandapp.web.interceptor.SecurityHandlerInterceptor
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.web.util.NestedServletException


import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDateTime

import static org.mockito.Matchers.anyLong
import static org.mockito.Matchers.eq
import static org.mockito.Mockito.mock
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.mockito.Mockito.when


import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class MovieControllerTest {
    private final ObjectMapper MAPPER = new ObjectMapper()
    @Mock
    private MovieService movieService
    @InjectMocks
    private MovieController movieController
    private MockMvc mockMvc
    private SecurityService securityService

    @Before
    void setup() {
        securityService = mock(SecurityService.class)
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new RequestParameterArgumentResolver())
                .addInterceptors(new SecurityHandlerInterceptor(securityService))
                .build()
    }


    @Test
    void testUpdate() {
        String token = UUID.randomUUID().toString()
        def user = new User.UserBuilder(id: 55L, role: Role.ADMIN).build()
        Optional<Session> sessionOptional = Optional.of(new Session(token, user, LocalDateTime.now()))
        when(securityService.getAuthorization(token)).thenReturn(sessionOptional)

        String requestBodyJson = MAPPER.writeValueAsString([id           : 25L,
                                                            nameRussian  : "NameRussian",
                                                            nameNative   : "NameNative",
                                                            yearOfRelease: "1994",
                                                            rating       : 8.99D,
                                                            price        : 150.15D,
                                                            picturePath  : "https://picture_path.png",
                                                            description  : "empty",
                                                            countries    : [1, 2, 3],
                                                            genres       : [1, 2]]
        )

        def movie = new Movie.MovieBuilder(id: 25L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
                countries: [Country.create(1), Country.create(2), Country.create(3)],
                genres: [Genre.create(1), Genre.create(2)]
        ).build()
        when(movieService.update(movie)).thenReturn(movie)

        def response = mockMvc.perform(put("/movie")
                .header("Token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson)
        ).andReturn().response

        assert response.status == HttpStatus.OK.value()

        def actualMovie = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovie = [id           : 25L,
                             nameRussian  : "NameRussian",
                             nameNative   : "NameNative",
                             yearOfRelease: "1994",
                             rating       : "8.99",
                             price        : "150.15",
                             picturePath  : "https://picture_path.png",
                             description  : "empty",
                             countries    : [[id: 1, name: null], [id: 2, name: null], [id: 3, name: null]],
                             genres       : [[id: 1, name: null], [id: 2, name: null]],
                             reviews      : []
        ]
        assert expectedMovie == actualMovie

    }

    @Test
    void testPartialUpdate() {
        String token = UUID.randomUUID().toString()
        def user = new User.UserBuilder(id: 55L, email: "testPartialUpdate@test.com", role: Role.ADMIN).build()
        Optional<Session> sessionOptional = Optional.of(new Session(token, user, LocalDateTime.now()))
        when(securityService.getAuthorization(token)).thenReturn(sessionOptional)

        String requestBodyJson = MAPPER.writeValueAsString([nameRussian  : "NameRussian",
                                                            nameNative   : "NameNative",
                                                            yearOfRelease: "1994",
                                                            rating       : 8.99D,
                                                            price        : 150.15D,
                                                            picturePath  : "https://picture_path.png",
                                                            description  : "MovieDescription!!!",
                                                            countries    : [1, 2, 3], genres: [1, 2]])

        def movie = new Movie.MovieBuilder(nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: [Country.create(1), Country.create(2), Country.create(3)],
                genres: [Genre.create(1), Genre.create(2)]
        ).build()

        Map<MovieFieldUpdate, Object> map = new HashMap<>()
        for (MovieFieldUpdate fieldUpdate : MovieFieldUpdate.values()) {
            map.put(fieldUpdate, fieldUpdate.getValue(movie))
        }

        Movie updatedMovie = new Movie.MovieBuilder(id: 26L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: [Country.create(1), Country.create(2), Country.create(3)],
                genres: [Genre.create(1), Genre.create(2)]
        ).build()
        when(movieService.partialUpdate(26L, new MovieUpdateDirections(map))).thenReturn(updatedMovie)

        def response = mockMvc.perform(patch("/movie/26")
                .header("Token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson)
        ).andReturn().response

        assert response.status == HttpStatus.OK.value()

        def actualMovie = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovie = [id           : 26L,
                             nameRussian  : "NameRussian",
                             nameNative   : "NameNative",
                             yearOfRelease: "1994",
                             rating       : "8.99",
                             price        : "150.15",
                             picturePath  : "https://picture_path.png",
                             description  : "MovieDescription!!!",
                             countries    : [[id: 1, name: null], [id: 2, name: null], [id: 3, name: null]],
                             genres       : [[id: 1, name: null], [id: 2, name: null]],
                             reviews      : []
        ]
        assert expectedMovie == actualMovie

    }

    @Test
    void testAdd() {

        String token = UUID.randomUUID().toString()
        def user = new User.UserBuilder(id: 55L, role: Role.ADMIN).build()
        Optional<Session> sessionOptional = Optional.of(new Session(token, user, LocalDateTime.now()))
        when(securityService.getAuthorization(token)).thenReturn(sessionOptional)

        String requestBodyJson = MAPPER.writeValueAsString([nameRussian  : "NameRussian",
                                                            nameNative   : "NameNative",
                                                            yearOfRelease: "1994",
                                                            rating       : 8.99D,
                                                            price        : 150.15D,
                                                            picturePath  : "https://picture_path.png",
                                                            description  : "MovieDescription!!!",
                                                            countries    : [1, 2, 3], genres: [1, 2]])


        def movie = new Movie.MovieBuilder(nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: [Country.create(1), Country.create(2), Country.create(3)],
                genres: [Genre.create(1), Genre.create(2)],
                reviews: []

        ).build()


        def addedMovie = new Movie.MovieBuilder(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "empty",
                countries: [Country.create(1), Country.create(2), Country.create(3)],
                genres: [Genre.create(1), Genre.create(2)]
        ).build()
        when(movieService.add(movie)).thenReturn(addedMovie)

        def response = mockMvc.perform(post("/movie")
                .header("Token", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBodyJson)
        ).andReturn().response

        assert response.status == HttpStatus.OK.value()


        def actualMovie = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovie = [id           : 1,
                             nameRussian  : "NameRussian",
                             nameNative   : "NameNative",
                             yearOfRelease: "1994",
                             rating       : "8.99",
                             price        : "150.15",
                             picturePath  : "https://picture_path.png",
                             description  : "empty",
                             countries    : [[id: 1, name: null], [id: 2, name: null], [id: 3, name: null]],
                             genres       : [[id: 1, name: null], [id: 2, name: null]],
                             reviews      : []
        ]
         assert expectedMovie == actualMovie

    }


    @Test
    void testGetByIdAndConvert() {

        def movie = new Movie.MovieBuilder(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: null, genres: null, reviews: null
        ).build()

        when(movieService.getById(1L, new RequestParameter(null, Currency.USD))).thenReturn(movie)
        def response = mockMvc.perform(get("/movie/1?currency=USD")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'



        def actualMovie = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovie = [id           : 1,
                             nameRussian  : "NameRussian",
                             nameNative   : "NameNative",
                             yearOfRelease: "1994",
                             rating       : "8.99",
                             price        : "150.15",
                             picturePath  : "https://picture_path.png",
                             description  : "MovieDescription!!!",
                             countries    : [], genres: [], reviews: []
        ]
        assert expectedMovie == actualMovie
    }

    @Test
    void testGetById() {

        def countries = [Country.create(1, "USA"), Country.create(2, "GB")]
        def genres = [Genre.create(1, "comedy"), Genre.create(2, "family")]
        def reviews = [new Review.ReviewBuilder(
                id: 1,
                text: "Excellent!!!",
                user: new User.UserBuilder(id: 1, nickname: "fName lName", email: null, password: null).build()
        ).build()]

        def movie = new Movie.MovieBuilder(id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png",
                description: "MovieDescription!!!",
                countries: countries, genres: genres, reviews: reviews
        ).build()

        when(movieService.getById(1)).thenReturn(movie)
        def response = mockMvc.perform(get("/movie/1")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def actualMovie = new JsonSlurper().parseText(response.contentAsString)


        def expectedMovie = [id           : 1,
                             nameRussian  : "NameRussian",
                             nameNative   : "NameNative",
                             yearOfRelease: "1994",
                             rating       : "8.99",
                             price        : "150.15",
                             picturePath  : "https://picture_path.png",
                             description  : "MovieDescription!!!",
                             countries    : [[id: 1, name: "USA"], [id: 2, name: "GB"]],
                             genres       : [[id: 1, name: "comedy"], [id: 2, name: "family"]],
                             reviews      : [[id: 1, text: "Excellent!!!", user: [id: 1, nickname: "fName lName"]]]
        ]

        assert expectedMovie == actualMovie

    }

    @Test
    void testGetAll() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()
        when(movieService.getAll()).thenReturn([movieFirst, movieSecond])
        def response = mockMvc.perform(get("/movie")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def actualMovies = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovies = [[id           : 1,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1994",
                               rating       : "8.99",
                               price        : "150.15",
                               picturePath  : "https://picture_path.png"
                              ],
                              [id           : 2,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1996",
                               rating       : "8.00",
                               price        : "150.00",
                               picturePath  : "https://picture_path2.png"]]

        assert actualMovies == expectedMovies
    }

    @Test
    void testGetAllByGenreId() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()
        when(movieService.getAll(1L)).thenReturn([movieFirst, movieSecond])

        def response = mockMvc.perform(get("/movie/genre/1")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def actualMovies = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovies = [[id           : 1,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1994",
                               rating       : "8.99",
                               price        : "150.15",
                               picturePath  : "https://picture_path.png"
                              ],
                              [id           : 2,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1996",
                               rating       : "8.00",
                               price        : "150.00",
                               picturePath  : "https://picture_path2.png"]]

        assert actualMovies == expectedMovies
    }

    @Test
    void testGetThreeRandomMovies() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()

        when(movieService.getThreeRandomMovies()).thenReturn([movieFirst, movieSecond])

        def response = mockMvc.perform(get("/movie/random")).andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'

        def actualMovies = new JsonSlurper().parseText(response.contentAsString)

        def expectedMovies = [[id           : 1,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1994",
                               rating       : "8.99",
                               price        : "150.15",
                               picturePath  : "https://picture_path.png"],
                              [id           : 2,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1996",
                               rating       : "8.00",
                               price        : "150.00",
                               picturePath  : "https://picture_path2.png"]]

        assert actualMovies == expectedMovies
    }

    @Test
    void testGetAllAndSort() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()
        def movieSecond = new Movie.MovieBuilder(
                id: 2L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1996, 1, 1),
                rating: 8D,
                price: 150D,
                picturePath: "https://picture_path2.png"
        ).build()



        def expectedMovies = [[id           : 1,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1994",
                               rating       : "8.99",
                               price        : "150.15",
                               picturePath  : "https://picture_path.png"
                              ],
                              [id           : 2,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1996",
                               rating       : "8.00",
                               price        : "150.00",
                               picturePath  : "https://picture_path2.png"]]

        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        def priceDescRequestParameter = new RequestParameter(priceDescSortDirection, null)
        when(movieService.getAll(eq(priceDescRequestParameter))).thenReturn([movieFirst, movieSecond])

        def priceDescResponse = mockMvc.perform(get("/movie?price=desc")).andReturn().response
        priceDescResponse.status == HttpStatus.OK.value()
        priceDescResponse.contentType == 'application/json;charset=UTF-8'
        def priceDescActualMovies = new JsonSlurper().parseText(priceDescResponse.contentAsString)
        assert priceDescActualMovies == expectedMovies


        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        def priceAscRequestParameter = new RequestParameter(priceAscSortDirection, null)
        when(movieService.getAll(eq(priceAscRequestParameter))).thenReturn([movieFirst, movieSecond])

        def priceAscResponse = mockMvc.perform(get("/movie?price=asc")).andReturn().response
        priceAscResponse.status == HttpStatus.OK.value()
        priceAscResponse.contentType == 'application/json;charset=UTF-8'
        def priceAscActualMovies = new JsonSlurper().parseText(priceAscResponse.contentAsString)
        assert priceAscActualMovies == expectedMovies


        def ratingDescSortDirection = new SortDirection("rating", SortOrder.DESC)
        def ratingDescRequestParameter = new RequestParameter(ratingDescSortDirection, null)
        when(movieService.getAll(eq(ratingDescRequestParameter))).thenReturn([movieFirst, movieSecond])

        def ratingDescResponse = mockMvc.perform(get("/movie?rating=desc")).andReturn().response
        ratingDescResponse.status == HttpStatus.OK.value()
        ratingDescResponse.contentType == 'application/json;charset=UTF-8'
        def ratingDescActualMovies = new JsonSlurper().parseText(ratingDescResponse.contentAsString)
        assert ratingDescActualMovies == expectedMovies
    }


    @Test
    void testGetAllByGenreIdAndSort() {
        def movieFirst = new Movie.MovieBuilder(
                id: 1L,
                nameRussian: "NameRussian",
                nameNative: "NameNative",
                yearOfRelease: LocalDate.of(1994, 1, 1),
                rating: 8.99D,
                price: 150.15D,
                picturePath: "https://picture_path.png"
        ).build()

        def expectedMovies = [[id           : 1,
                               nameRussian  : "NameRussian",
                               nameNative   : "NameNative",
                               yearOfRelease: "1994",
                               rating       : "8.99",
                               price        : "150.15",
                               picturePath  : "https://picture_path.png"
                              ]]


        def priceDescSortDirection = new SortDirection("price", SortOrder.DESC)
        def priceDescRequestParameter = new RequestParameter(priceDescSortDirection, null)
        when(movieService.getAll(anyLong(), eq(priceDescRequestParameter))).thenReturn([movieFirst])

        def priceDescResponse = mockMvc.perform(get("/movie/genre/1?price=desc")).andReturn().response
        priceDescResponse.status == HttpStatus.OK.value()
        priceDescResponse.contentType == 'application/json;charset=UTF-8'
        def priceDescActualMovies = new JsonSlurper().parseText(priceDescResponse.contentAsString)
        assert priceDescActualMovies == expectedMovies


        def priceAscSortDirection = new SortDirection("price", SortOrder.ASC)
        def priceAscRequestParameter = new RequestParameter(priceAscSortDirection, null)
        when(movieService.getAll(anyLong(), eq(priceAscRequestParameter))).thenReturn([movieFirst])

        def priceAscResponse = mockMvc.perform(get("/movie/genre/1?price=asc")).andReturn().response
        priceAscResponse.status == HttpStatus.OK.value()
        priceAscResponse.contentType == 'application/json;charset=UTF-8'
        def priceAscActualMovies = new JsonSlurper().parseText(priceAscResponse.contentAsString)
        assert priceAscActualMovies == expectedMovies


        def ratingDescSortDirection = new SortDirection("rating", SortOrder.DESC)
        def ratingDescRequestParameter = new RequestParameter(ratingDescSortDirection, null)
        when(movieService.getAll(anyLong(), eq(ratingDescRequestParameter))).thenReturn([movieFirst])

        def ratingDescResponse = mockMvc.perform(get("/movie/genre/1?rating=desc")).andReturn().response
        ratingDescResponse.status == HttpStatus.OK.value()
        ratingDescResponse.contentType == 'application/json;charset=UTF-8'
        def ratingDescActualMovies = new JsonSlurper().parseText(ratingDescResponse.contentAsString)
        assert ratingDescActualMovies == expectedMovies
    }

    @Test
    void testNegativeSortByRatingAscNotSupported() {
        def ex = expectThrown(NestedServletException) {
            mockMvc.perform(get("/movie/genre/1?rating=asc"))
        }
        assert ex.getCause().class == IllegalArgumentException.class
        assert ex.getCause().getMessage() == "Incorrect sort parameters, expected movies sorting by rating (desc)"
    }

    @Test
    void testNegativeSortByIncorrectRequestParam() {
        def ex = expectThrown(NestedServletException) {
            mockMvc.perform(get("/movie/genre/1?rating=IncorrectRequestParam"))
        }
        assert ex.getCause().class == IllegalArgumentException.class
        assert ex.getCause().getMessage() == "No sorting order for value: IncorrectRequestParam"
    }

    @Test
    void testNegativeGetByIdAndConvert() {
        def ex = expectThrown(NestedServletException) {
            mockMvc.perform(get("/movie/1?currency=djfksjhfjksh"))
        }
        assert ex.getCause().class == IllegalArgumentException.class
        assert ex.getCause().getMessage() == "No value for: djfksjhfjksh"
    }


    def static expectThrown(Class expectedThrowable = Throwable, Closure closure) {
        try {
            closure()
        } catch (Throwable t) {
            if (!expectedThrowable.isInstance(t)) {
                throw t
            }
            return t
        }
        throw new AssertionError("Expected Throwable $expectedThrowable not thrown")
    }


}
