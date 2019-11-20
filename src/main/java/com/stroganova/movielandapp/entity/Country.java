package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.CountryDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@JsonView(View.Summary.class)
@JsonDeserialize(using = CountryDeserializer.class)
public class Country {
    private final long id;
    private final String name;

    private Country(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Country create(long id) {
        return new Country(id, null);
    }

    public static Country create(long id, String name) {
        return new Country(id, name);
    }
}
