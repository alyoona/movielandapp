package com.stroganova.movielandapp.web.json.deseializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.stroganova.movielandapp.entity.Country;

import java.io.IOException;

public class CountryDeserializer extends StdDeserializer<Country> {

    public CountryDeserializer() {
        super(Country.class);
    }

    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        long id = jsonParser.getLongValue();
        return Country.create(id);
    }
}
