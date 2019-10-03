package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.GenreDeserializer;
import lombok.Value;

@Value
@JsonView(View.Summary.class)
@JsonDeserialize(using = GenreDeserializer.class)
public class Genre {
    private long id;
    private String name;
}
