package com.stroganova.movielandapp.enums;

public enum MovieRequestParam {
    RATING("rating"), PRICE("price");

    private final String name;

    MovieRequestParam(String name) {
        this.name = name;
    }

    public static MovieRequestParam getByName(String name) {
        MovieRequestParam[] values = values();
        for (MovieRequestParam param : values) {
            if (param.getName().equalsIgnoreCase(name)) {
                return param;
            }
        }
        throw new IllegalArgumentException("No movie request parameter for name: " + name);
    }

    public String getName() {
        return name;
    }


}
