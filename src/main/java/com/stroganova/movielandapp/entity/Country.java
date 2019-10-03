package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.CountryDeserializer;
import lombok.Data;

@Data
@JsonView(View.Summary.class)
@JsonDeserialize(using = CountryDeserializer.class)
public class Country {
    private long id;
    private String name;
}
