package com.stroganova.movielandapp.request;

public enum Currency {
    USD("USD"), EUR("EUR");

    private final String name;

    Currency (String currencyValue) {
        this.name = currencyValue;
    }

    public static Currency getByName(String name) {
        Currency[] values = values();
        for (Currency value : values) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No value for: " + name);
    }

    public String getName() {
        return name;
    }

}
