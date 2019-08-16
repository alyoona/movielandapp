package com.stroganova.movielandapp.request;


import lombok.Data;

@Data
public class SortDirection {

    private String field;
    private SortOrder orderValue;

    public String getFieldAndValue() {
        return getField() + " " + orderValue.getName();
    }
}

