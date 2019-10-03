package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.YearLocalDateDeserializer;
import com.stroganova.movielandapp.web.json.serializer.DoubleSerializer;
import com.stroganova.movielandapp.web.json.serializer.YearLocalDateSerializer;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonView(View.Summary.class)
public class Movie {

    private long id;
    private String nameRussian;
    private String nameNative;
    @JsonSerialize(using = YearLocalDateSerializer.class)
    @JsonDeserialize(using = YearLocalDateDeserializer.class)
    private LocalDate yearOfRelease;

    @JsonSerialize(using = DoubleSerializer.class)
    private double rating;
    @JsonSerialize(using = DoubleSerializer.class)
    private double price;
    private String picturePath;

    @JsonView(View.MovieDetail.class)
    private String description;

    @JsonView(View.MovieDetail.class)
    private List<Country> countries;

    @JsonView(View.MovieDetail.class)
    private List<Genre> genres;

    @JsonView(View.MovieDetail.class)
    private List<Review> reviews;
}
