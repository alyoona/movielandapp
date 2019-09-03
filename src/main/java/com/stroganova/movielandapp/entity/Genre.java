package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.view.View;
import lombok.Value;

@Value
@JsonView(View.Summary.class)
public class Genre {
    private long id;
    private String name;
}
