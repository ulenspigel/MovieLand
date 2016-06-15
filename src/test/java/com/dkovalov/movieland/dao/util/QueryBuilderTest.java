package com.dkovalov.movieland.dao.util;

import com.dkovalov.movieland.dto.MovieRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
public class QueryBuilderTest {
    @Autowired
    QueryBuilder queryBuilder;

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
        MovieRequest request = new MovieRequest();
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
        MovieRequest request = new MovieRequest();
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
