package com.stroganova.movielandapp.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.entity.Token;
import com.stroganova.movielandapp.entity.User;
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

    @JsonView(View.Token.class)
    @PostMapping("/login")
    public Token login(@RequestBody User user) {
        try {
            return securityService.login(user);
        } catch (NotAuthenticatedException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader("Uuid") String uuid) {
        securityService.logout(uuid);

    }


}
