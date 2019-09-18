package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.ReviewDeserializer;
import lombok.Data;

@Data
@JsonDeserialize(using = ReviewDeserializer.class)
@JsonView(View.Summary.class)
public class Review {
    private long id;
    private String text;
    private User user;
    @JsonIgnore
    private Movie movie;
}
