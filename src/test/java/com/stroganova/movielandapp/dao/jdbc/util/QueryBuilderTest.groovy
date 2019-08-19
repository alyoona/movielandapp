package com.stroganova.movielandapp.dao.jdbc.util

import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
import org.junit.Test


class QueryBuilderTest {

    @Test
    void testGetOrderBySql() {
        def sortDirection = new SortDirection(field: "fieldName", orderValue: SortOrder.ASC)
        def query = "select id from table"
        def expectedSql = query + " ORDER BY " + sortDirection.getFieldAndValue()
        def actualSql = QueryBuilder.getOrderBySql(query, sortDirection)
        assert expectedSql == actualSql
    }
}
