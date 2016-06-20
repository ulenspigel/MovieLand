package com.dkovalov.movieland.dao.util;

import com.dkovalov.movieland.dto.MovieSearchRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryBuilderTest {
    QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    public void testGetMoviesOrderClause() {
        assertEquals(queryBuilder.getMoviesOrderClause("asc", "desc"), " order by rating asc,price desc");
        assertEquals(queryBuilder.getMoviesOrderClause(null, "asc"), " order by price asc");
        assertEquals(queryBuilder.getMoviesOrderClause("desc", null), " order by rating desc");
        assertEquals(queryBuilder.getMoviesOrderClause(null, null), "");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testValidateSortOrder() {
        queryBuilder.validateSortOrder("ascc");
    }

    @Test
    public void testGetMoviesFilterPredicate() {
        MovieSearchRequest request = new MovieSearchRequest();
        String expectedResult = "";
        assertEquals(queryBuilder.getMoviesFilterPredicate(request), expectedResult);
        request.setTitle("Test title");
        expectedResult += " where movie_title = ?";
        assertEquals(queryBuilder.getMoviesFilterPredicate(request), expectedResult);
        request.setYear(1900);
        expectedResult += " and year = ?";
        assertEquals(queryBuilder.getMoviesFilterPredicate(request), expectedResult);
    }

    @Test
    public void testGetMoviesFilterParams() {
        MovieSearchRequest request = new MovieSearchRequest();
        Object[] params = queryBuilder.getMoviesFilterParams(request);
        assertEquals(params.length, 0);
        request.setCountry("Test country");
        params = queryBuilder.getMoviesFilterParams(request);
        assertEquals(params.length, 1);
        assertEquals(params[0], "Test country");
        request.setGenre("Test genre");
        params = queryBuilder.getMoviesFilterParams(request);
        assertEquals(params.length, 2);
        assertEquals(params[0], "Test genre");
        assertEquals(params[1], "Test country");
    }
}
