package com.stroganova.movielandapp.request;

import com.stroganova.movielandapp.entity.Movie;

import java.util.function.Function;

public enum MovieFieldUpdate {
    NAME_RUSSIAN("name_russian", "nameRussian", Movie::getNameRussian) ,
    NAME_NATIVE("name_native", "nameNative", Movie::getNameNative),
    YEAR_OF_RELEASE("year", "yearOfRelease", Movie::getYearOfRelease),
    DESCRIPTION("description", "description", Movie::getDescription),
    RATING("rating", "rating", Movie::getRating),
    PRICE("price", "price", Movie::getPrice),
    PICTURE_PATH("picture_path", "picturePath", Movie::getPicturePath),
    COUNTRIES("countries", "countries", Movie::getCountries),
    GENRES("genres", "genres", Movie::getGenres),
    REVIEWS("reviews", "reviews", Movie::getReviews);

    private final String dbName;
    private final String jsonName;
    private final Function<Movie, Object> getMovieField;

    MovieFieldUpdate(String dbName, String jsonName, Function<Movie, Object> getMovieField) {
        this.dbName = dbName;
        this.jsonName = jsonName;
        this.getMovieField = getMovieField;
    }

    public static MovieFieldUpdate getByJsonName(String jsonName) {
        MovieFieldUpdate[] values = values();
        for (MovieFieldUpdate value : values) {
            if (value.getJsonName().equalsIgnoreCase(jsonName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No value for: " + jsonName);
    }

    public Object getValue(Movie movie) {
        return getMovieField.apply(movie);
    }

    public String getDbName() {
        return dbName;
    }

    public String getJsonName() {
        return jsonName;
    }
}
