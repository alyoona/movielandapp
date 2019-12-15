package com.stroganova.movielandapp.security.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.entity.User;
import com.stroganova.movielandapp.views.SessionView;
import com.stroganova.movielandapp.web.json.serializer.NicknameUserSerializer;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Session {
    @JsonView(SessionView.Session.class)
    private final String uuid;
    @JsonView(SessionView.Session.class)
    @JsonSerialize(using = NicknameUserSerializer.class)
    @JsonProperty("nickname")
    private final User user;
    private final LocalDateTime expirationDate;
}
