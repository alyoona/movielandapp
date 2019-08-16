package com.stroganova.movielandapp.request;

public enum SortOrder {
    DESC("desc"), ASC("asc");

    private final String name;

    SortOrder(String name) {
        this.name = name;
    }

    public static SortOrder getByName(String name) {
        SortOrder[] values = values();
        for (SortOrder value : values) {
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
