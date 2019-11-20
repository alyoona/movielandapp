package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.view.View;
import com.stroganova.movielandapp.web.json.deseializer.YearLocalDateDeserializer;
import com.stroganova.movielandapp.web.json.serializer.DoubleSerializer;
import com.stroganova.movielandapp.web.json.serializer.YearLocalDateSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Movie.MovieBuilder.class)
@JsonView(View.Summary.class)
@Getter
@EqualsAndHashCode
public class Movie {

    private final long id;
    private final String nameRussian;
    private final String nameNative;
    @JsonSerialize(using = YearLocalDateSerializer.class)
    private final LocalDate yearOfRelease;
    @JsonSerialize(using = DoubleSerializer.class)
    private final double rating;
    @JsonSerialize(using = DoubleSerializer.class)
    private final double price;
    private final String picturePath;

    @JsonView(View.MovieDetail.class)
    private final String description;

    @JsonView(View.MovieDetail.class)
    private final List<Country> countries;

    @JsonView(View.MovieDetail.class)
    private final List<Genre> genres;

    @JsonView(View.MovieDetail.class)
    private final List<Review> reviews;

    private Movie(MovieBuilder movieBuilder) {
        this.id = movieBuilder.id;
        this.nameRussian = movieBuilder.nameRussian;
        this.nameNative = movieBuilder.nameNative;
        this.yearOfRelease = movieBuilder.yearOfRelease;
        this.rating = movieBuilder.rating;
        this.price = movieBuilder.price;
        this.picturePath = movieBuilder.picturePath;
        this.description = movieBuilder.description;
        this.countries = movieBuilder.countries;
        this.genres = movieBuilder.genres;
        this.reviews = movieBuilder.reviews;
    }

    @JsonPOJOBuilder
    public static class MovieBuilder {
        private long id;
        private String nameRussian;
        private String nameNative;
        private LocalDate yearOfRelease;
        private double rating;
        private double price;
        private String picturePath;
        private String description;
        private List<Country> countries;
        private List<Genre> genres;
        private List<Review> reviews;

        public MovieBuilder newMovie(Movie movie) {
            this.id = movie.getId();
            this.nameRussian = movie.getNameRussian();
            this.nameNative = movie.getNameNative();
            this.yearOfRelease = movie.getYearOfRelease();
            this.rating = movie.getRating();
            this.price = movie.getPrice();
            this.picturePath = movie.getPicturePath();
            this.description = movie.getDescription();
            this.countries = movie.getCountries();
            this.genres = movie.getGenres();
            this.reviews = movie.getReviews();
            return this;
        }

        @JsonProperty("id")
        public MovieBuilder setId(long id) {
            this.id = id;
            return this;
        }

        @JsonProperty("nameRussian")
        public MovieBuilder setNameRussian(String nameRussian) {
            this.nameRussian = nameRussian;
            return this;

        }
        @JsonProperty("nameNative")
        public MovieBuilder setNameNative(String nameNative) {
            this.nameNative = nameNative;
            return this;

        }
        @JsonProperty("yearOfRelease")
        @JsonDeserialize(using = YearLocalDateDeserializer.class)
        public MovieBuilder setYearOfRelease(LocalDate yearOfRelease) {
            this.yearOfRelease = yearOfRelease;
            return this;

        }
        @JsonProperty("rating")
        public MovieBuilder setRating(double rating) {
            this.rating = rating;
            return this;

        }
        @JsonProperty("price")
        public MovieBuilder setPrice(double price) {
            this.price = price;
            return this;
        }
        @JsonProperty("picturePath")
        public MovieBuilder setPicturePath(String picturePath) {
            this.picturePath = picturePath;
            return this;
        }
        @JsonProperty("description")
        public MovieBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        @JsonProperty("countries")
        public MovieBuilder setCountries(List<Country> countries) {
            this.countries = countries != null ? new ArrayList<>(countries) : Collections.EMPTY_LIST;
            return this;
        }
        @JsonProperty("genres")
        public MovieBuilder setGenres(List<Genre> genres) {
            this.genres = genres != null ? new ArrayList<>(genres) : Collections.EMPTY_LIST;
            return this;

        }
        @JsonProperty("reviews")
        public MovieBuilder setReviews(List<Review> reviews) {
            this.reviews = reviews != null ? new ArrayList<>(reviews) : Collections.EMPTY_LIST;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }


    public List<Country> getCountries() {
        return countries != null ? new ArrayList<>(countries) : Collections.EMPTY_LIST;
    }

    public List<Genre> getGenres() {
        return genres != null ? new ArrayList<>(genres) : Collections.EMPTY_LIST;
    }

    public List<Review> getReviews() {
        return reviews != null ? new ArrayList<>(reviews) : Collections.EMPTY_LIST;
    }





}
