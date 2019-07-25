package com.stroganova.movielandapp.web.entity;


import lombok.Data;

@Data
public class SortDirection {

    private String field;
    private String orderValue;

    public String getFieldAndValue() {
        return getField() + " " + getOrderValue();
    }
}

