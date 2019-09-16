package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
    @JsonView(View.MovieDetail.class)
    private long id;
    @JsonView(View.MovieDetail.class)
    private String nickname;
    private String email;
    private String password;
}
