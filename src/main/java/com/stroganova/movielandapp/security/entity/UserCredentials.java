package com.stroganova.movielandapp.security.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserCredentials {
    private final String email;
    private final String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserCredentials(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
