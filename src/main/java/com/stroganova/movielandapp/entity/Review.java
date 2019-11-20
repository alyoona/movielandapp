package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.ReviewDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@JsonDeserialize(using = ReviewDeserializer.class)
@JsonView(View.Summary.class)
public class Review {
    private long id;
    private String text;
    private User user;
    @JsonIgnore
    private Movie movie;

    private Review(ReviewBuilder reviewBuilder) {
        this.id = reviewBuilder.id;
        this.text = reviewBuilder.text;
        this.user = reviewBuilder.user;
        this.movie = reviewBuilder.movie;
    }

    public static class ReviewBuilder {
        private long id;
        private String text;
        private User user;
        private Movie movie;

        public ReviewBuilder newReview(Review review) {
            this.id = review.getId();
            this.text = review.getText();
            this.user = review.getUser();
            this.movie = review.getMovie();
            return this;
        }

        public ReviewBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ReviewBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public ReviewBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public ReviewBuilder setMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Review build(){
            return new Review(this);
        }
    }


}
