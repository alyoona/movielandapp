package com.stroganova.movielandapp.request;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SortDirection {

    private String field;
    private SortOrder orderValue;

    public String getFieldAndValue() {
        return getField() + " " + orderValue.getName();
    }


}

