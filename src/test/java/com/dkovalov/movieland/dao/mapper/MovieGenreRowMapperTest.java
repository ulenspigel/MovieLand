package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.dto.MovieGenre;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieGenreRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1).thenReturn(10);
        when(resultSet.getString(any(String.class))).thenReturn("Test genre");

        MovieGenreRowMapper movieGenreMapper = new MovieGenreRowMapper();
        MovieGenre movieGenre = movieGenreMapper.mapRow(resultSet, 0);
        assertEquals(movieGenre.getMovieId(), 1);
        assertEquals(movieGenre.getGenre().getId(), 10);
        assertEquals(movieGenre.getGenre().getName(), "Test genre");
    }
}
