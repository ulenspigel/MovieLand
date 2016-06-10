package com.dkovalov.movieland.dao.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class QueryBuilderTest {
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetMoviesOrderClause() {
        assertEquals(QueryBuilder.getMoviesOrderClause("asc", "desc"), " order by rating asc,price desc");
        assertEquals(QueryBuilder.getMoviesOrderClause(null, "asc"), " order by price asc");
        assertEquals(QueryBuilder.getMoviesOrderClause("desc", null), " order by rating desc");
        assertEquals(QueryBuilder.getMoviesOrderClause(null, null), "");
        QueryBuilder.getMoviesOrderClause("ascc","deesc");
    }
}
