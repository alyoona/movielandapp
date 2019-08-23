package com.stroganova.movielandapp.dao.jdbc.util;

import com.stroganova.movielandapp.request.SortDirection;

public class QueryBuilder {

    public static String getOrderBySql (String sql, SortDirection sortDirection) {
        return sql + " ORDER BY " + sortDirection.getFieldAndValue();
    }
}
