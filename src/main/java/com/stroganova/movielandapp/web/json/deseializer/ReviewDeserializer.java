package com.stroganova.movielandapp.web.json.deseializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.stroganova.movielandapp.entity.Movie;
import com.stroganova.movielandapp.entity.Review;

import java.io.IOException;

public class ReviewDeserializer extends StdDeserializer<Review> {

    public ReviewDeserializer() {
        super(Review.class);
    }

    @Override
    public Review deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        long movieId = node.get("movieId").asLong();
        Review review = new Review();
        review.setText(node.get("text").asText());
        Movie movie = new Movie();
        movie.setId(movieId);
        review.setMovie(movie);
        return review;
    }
}
