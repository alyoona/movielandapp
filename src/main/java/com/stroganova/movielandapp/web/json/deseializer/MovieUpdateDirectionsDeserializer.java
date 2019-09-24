package com.stroganova.movielandapp.web.json.deseializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.request.MovieFieldUpdate;
import com.stroganova.movielandapp.request.MovieUpdateDirections;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MovieUpdateDirectionsDeserializer extends StdDeserializer<MovieUpdateDirections> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieUpdateDirectionsDeserializer() {
        super(MovieUpdateDirections.class);
    }

    @Override
    public MovieUpdateDirections deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String jsonRequest = jsonParser.readValueAsTree().toString();

        Movie movie = objectMapper.readValue(jsonRequest, Movie.class);

        JsonNode node = objectMapper.readTree(jsonRequest);
        Iterator<String> iterator = node.fieldNames();
        Map<MovieFieldUpdate, Object> map = new HashMap<>();
        while (iterator.hasNext()) {
            MovieFieldUpdate movieFieldUpdate = MovieFieldUpdate.getByJsonName(iterator.next());
            map.put(movieFieldUpdate, movieFieldUpdate.getValue(movie));
        }

        return new MovieUpdateDirections(map);
    }
}
