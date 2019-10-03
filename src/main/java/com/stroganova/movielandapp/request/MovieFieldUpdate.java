package com.stroganova.movielandapp.request;

import com.stroganova.movielandapp.entity.Movie;

public enum MovieFieldUpdate {
    NAME_RUSSIAN("name_russian", "nameRussian") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getNameRussian();
        }
    },
    NAME_NATIVE("name_native", "nameNative") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getNameNative();
        }
    },
    YEAR_OF_RELEASE("year", "yearOfRelease") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getYearOfRelease();
        }
    },
    DESCRIPTION("description", "description") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getDescription();
        }
    },
    RATING("rating", "rating") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getRating();
        }
    },
    PRICE("price", "price") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getPrice();
        }
    },
    PICTURE_PATH("picture_path", "picturePath") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getPicturePath();
        }
    },

    COUNTRIES("countries", "countries") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getCountries();
        }
    },

    GENRES("genres", "genres") {
        @Override
        public Object getValue(Movie movie) {
            return movie.getGenres();
        }
    };

    String dbName;
    String jsonName;

    MovieFieldUpdate(String dbName, String jsonName) {
        this.dbName = dbName;
        this.jsonName = jsonName;

    }

    abstract public Object getValue(Movie movie);

    public static MovieFieldUpdate getByJsonName(String jsonName) {
        MovieFieldUpdate[] values = values();
        for (MovieFieldUpdate value : values) {
            if (value.getJsonName().equalsIgnoreCase(jsonName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No value for: " + jsonName);
    }

    public String getDbName() {
        return dbName;
    }

    public String getJsonName() {
        return jsonName;
    }
}
