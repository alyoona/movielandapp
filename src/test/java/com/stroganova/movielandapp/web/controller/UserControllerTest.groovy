package com.stroganova.movielandapp.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.stroganova.movielandapp.entity.Session
import com.stroganova.movielandapp.entity.User
import com.stroganova.movielandapp.entity.UserCredentials
import com.stroganova.movielandapp.exception.NotAuthenticatedException
import com.stroganova.movielandapp.service.SecurityService
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.stubbing.answers.DoesNothing
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import java.time.LocalDateTime

import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper()

    @Mock
    private SecurityService securityService
    @InjectMocks
    private UserController userController
    private MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    void testLogin() {


        def user = new UserCredentials("ronald.reynolds66@example.com", "paco")
        String requestBodyJson = MAPPER.writeValueAsString(user)
        def loggedIdUser = new User.UserBuilder(nickname: "Big Ben").build()
        when(securityService.login(user)).thenReturn(new Session("uuid123456789", loggedIdUser, LocalDateTime.now()))

        def response = mockMvc.perform(post("/login").content(requestBodyJson).contentType('application/json'))
                .andReturn().response
        response.status == HttpStatus.OK.value()
        response.contentType.contains('application/json')
        response.contentType == 'application/json;charset=UTF-8'


        def actualResponseBody = new JsonSlurper().parseText(response.contentAsString)
        def expectedResponseBody = [uuid: "uuid123456789", nickname: "Big Ben"]

        assert expectedResponseBody == actualResponseBody

    }

    @Test
    void testLogout() {
        def token = UUID.randomUUID().toString()
        when(securityService.logout(token)).then(new DoesNothing())
        def response = mockMvc.perform(delete("/logout").header("Token", token)).andReturn().response
        assert response.status == HttpStatus.OK.value()
    }

    @Test
    void testLoginBadRequest(){
        def user = new UserCredentials("ronald.reynolds66@example.com", "paco")
        String requestBodyJson = MAPPER.writeValueAsString(user)
        when(securityService.login(user)).thenThrow(NotAuthenticatedException.class)
        def response = mockMvc.perform(post("/login").content(requestBodyJson).contentType('application/json'))
                .andReturn().response

        assert response.status == HttpStatus.UNAUTHORIZED.value()

    }



}
