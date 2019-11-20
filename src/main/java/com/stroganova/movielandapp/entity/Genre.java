package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.GenreDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@JsonView(View.Summary.class)
@JsonDeserialize(using = GenreDeserializer.class)
public class Genre {
    private final long id;
    private final String name;

    private Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Genre create(long id) {
        return new Genre(id, null);
    }

    public static Genre create(long id, String name) {
        return new Genre(id, name);
    }


}
