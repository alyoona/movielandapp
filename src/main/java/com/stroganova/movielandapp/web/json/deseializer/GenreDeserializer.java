package com.stroganova.movielandapp.web.json.deseializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.stroganova.movielandapp.entity.Genre;

import java.io.IOException;

public class GenreDeserializer extends StdDeserializer<Genre> {

    public GenreDeserializer() {
        super(Genre.class);
    }

    @Override
    public Genre deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return Genre.create(jsonParser.getLongValue());
    }
}
