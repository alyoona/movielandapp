package com.stroganova.movielandapp.dao.jdbc.util

import com.stroganova.movielandapp.request.MovieFieldUpdate
import com.stroganova.movielandapp.request.SortDirection
import com.stroganova.movielandapp.request.SortOrder
import org.junit.Test


class QueryBuilderTest {

    @Test
    void testGetOrderBySql() {
        def sortDirection = new SortDirection("fieldName", SortOrder.ASC)
        def query = "select id from table"
        def expectedSql = query + " ORDER BY " + sortDirection.getFieldAndValue()
        def actualSql = QueryBuilder.getOrderBySql(query, sortDirection)
        assert expectedSql == actualSql
    }

    @Test
    void testGetUpdateMovieSql() {
        def list = [MovieFieldUpdate.NAME_RUSSIAN, MovieFieldUpdate.NAME_NATIVE,
                    MovieFieldUpdate.YEAR_OF_RELEASE, MovieFieldUpdate.DESCRIPTION,
                    MovieFieldUpdate.RATING, MovieFieldUpdate.PRICE]
        def actualSql = QueryBuilder.getUpdateSql(list)
        assert actualSql == "UPDATE movieland.movie  SET name_russian = :name_russian, name_native = :name_native," +
                " year = :year, description = :description, rating = :rating, price = :price WHERE  id = :id"
    }

    @Test
    void testGetAllMovieFieldsUpdateSql() {
        def actualSql = QueryBuilder.getAllMovieFieldsUpdateSql()
        assert actualSql == "UPDATE movieland.movie  SET name_russian = :name_russian, name_native = :name_native," +
                " year = :year, description = :description, rating = :rating, price = :price WHERE  id = :id"

    }
}
