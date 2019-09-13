package com.stroganova.movielandapp.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.entity.UserCredentials;
import com.stroganova.movielandapp.exception.NotAuthenticatedException;
import com.stroganova.movielandapp.service.SecurityService;
import com.stroganova.movielandapp.view.View;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    @NonNull SecurityService securityService;

    @JsonView(View.Session.class)
    @PostMapping("/login")
    public com.stroganova.movielandapp.entity.Session login(@RequestBody UserCredentials userCredentials) {
        try {
            return securityService.login(userCredentials);
        } catch (NotAuthenticatedException ex) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        }

    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader("Token") String token) {
        securityService.logout(token);

    }


}
