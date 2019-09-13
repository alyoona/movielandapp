package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.serializer.NicknameUserSerializer;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Session {
    @JsonView(View.Session.class)
    String uuid;
    @JsonView(View.Session.class)
    @JsonSerialize(using = NicknameUserSerializer.class)
    @JsonProperty("nickname")
    User user;
    LocalDateTime expirationDate;
}
