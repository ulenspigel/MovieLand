package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Genre;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        when(resultSet.getString(any(String.class))).thenReturn("test_genre");

        GenreRowMapper genreMapper = new GenreRowMapper();
        Genre genre = genreMapper.mapRow(resultSet, 0);
        assertEquals(genre.getId(), 1);
        assertEquals(genre.getName(), "test_genre");
    }
}
