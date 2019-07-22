package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.web.json.serializer.DoubleSerializer;
import com.stroganova.movielandapp.web.json.serializer.YearLocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Movie {
    private long id;
    private String nameRussian;
    private String nameNative;
    @JsonSerialize(using = YearLocalDateSerializer.class)
    private LocalDate yearOfRelease;
    @JsonSerialize(using = DoubleSerializer.class)
    private double rating;
    @JsonSerialize(using = DoubleSerializer.class)
    private double price;
    private String picturePath;
}
