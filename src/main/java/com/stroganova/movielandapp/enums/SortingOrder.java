package com.stroganova.movielandapp.enums;

public enum SortingOrder {
    DESC("desc"), ASC("asc");

    private final String name;

    SortingOrder(String name) {
        this.name = name;
    }

    public static SortingOrder getByName(String name) {
        SortingOrder[] values = values();
        for (SortingOrder value : values) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No sorting order for value: " + name);
    }

    public String getName() {
        return name;
    }
}
