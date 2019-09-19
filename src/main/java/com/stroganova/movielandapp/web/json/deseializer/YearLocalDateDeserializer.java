package com.stroganova.movielandapp.web.json.deseializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class YearLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public YearLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!"yearOfRelease".equalsIgnoreCase(jsonParser.currentName())) {
            throw new IOException("Expected yearOfRelease value");
        }
        int year = Integer.valueOf(jsonParser.getText());
        return LocalDate.of(year, 1, 1);

    }
}
