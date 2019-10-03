package com.stroganova.movielandapp.dao.jdbc.util;

import com.stroganova.movielandapp.request.MovieFieldUpdate;
import com.stroganova.movielandapp.request.SortDirection;

import java.util.*;

public class QueryBuilder {

    public static String getOrderBySql(String sql, SortDirection sortDirection) {
        return sql + " ORDER BY " + sortDirection.getFieldAndValue();
    }

    public static String getUpdateSql(List<MovieFieldUpdate> updates) {
        String update = "UPDATE movieland.movie ";
        String set = " SET ";
        String where = " WHERE ";
        String prefix = update + set;
        String suffix = where + " id = :id";
        StringJoiner stringJoiner = new StringJoiner(", ", prefix, suffix);
        for (MovieFieldUpdate field : updates) {
            String fieldNameDb = field.getDbName();
            stringJoiner.add(fieldNameDb + " = :" + fieldNameDb);
        }
        return stringJoiner.toString();

    }

    public static String getAllMovieFieldsUpdateSql() {
        List<MovieFieldUpdate> values = Arrays.asList(
                MovieFieldUpdate.NAME_RUSSIAN, MovieFieldUpdate.NAME_NATIVE,
                MovieFieldUpdate.YEAR_OF_RELEASE, MovieFieldUpdate.DESCRIPTION,
                MovieFieldUpdate.RATING, MovieFieldUpdate.PRICE);
        return getUpdateSql(values);
    }
}
