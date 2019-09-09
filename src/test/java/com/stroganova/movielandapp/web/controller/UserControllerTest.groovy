package com.stroganova.movielandapp.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.stroganova.movielandapp.entity.Token
import com.stroganova.movielandapp.entity.User
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

        ObjectMapper mapper = new ObjectMapper()
        def user = new User(email: "ronald.reynolds66@example.com", password: "paco")
        String requestBodyJson = mapper.writeValueAsString(user)
        def loggedIdUser = new User(nickname: "Big Ben")
        when(securityService.login(user)).thenReturn(new Token("uuid123456789", loggedIdUser, LocalDateTime.now()))

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
        def uuid = UUID.randomUUID().toString()
        when(securityService.logout(uuid)).then(new DoesNothing())
        def response = mockMvc.perform(delete("/logout").header("Uuid", uuid)).andReturn().response
        assert response.status == HttpStatus.OK.value()
    }

    @Test
    void testLoginBadRequest(){
        ObjectMapper mapper = new ObjectMapper()
        def user = new User(email: "ronald.reynolds66@example.com", password: "paco")
        String requestBodyJson = mapper.writeValueAsString(user)
        when(securityService.login(user)).thenThrow(NotAuthenticatedException.class)
        def response = mockMvc.perform(post("/login").content(requestBodyJson).contentType('application/json'))
                .andReturn().response

        assert response.status == HttpStatus.BAD_REQUEST.value()

    }



}
