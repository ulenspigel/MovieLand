package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Movie;
import org.junit.Test;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1).thenReturn(2000);
        when(resultSet.getString(any(String.class))).thenReturn("test title").thenReturn("test original title")
                .thenReturn("test description");
        when(resultSet.getBigDecimal(any(String.class))).thenReturn(new BigDecimal(10.0)).thenReturn(new BigDecimal(100));

        MovieRowMapper movieMapper = new MovieRowMapper();
        Movie movie = movieMapper.mapRow(resultSet, 0);
        assertEquals(movie.getId(), 1);
        assertEquals(movie.getTitle(), "test title");
        assertEquals(movie.getOriginalTitle(), "test original title");
        assertEquals(movie.getYear(), 2000);
        assertEquals(movie.getDescription(), "test description");
        assertEquals(movie.getRating(), new BigDecimal(10.0));
        assertEquals(movie.getPrice(), new BigDecimal(100));
    }
}
