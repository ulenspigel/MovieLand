package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/resources/spring/root-context.xml")
public class GenreRowMapperTest {
    @Autowired
    private GenreRowMapper genreMapper;

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        when(resultSet.getString(any(String.class))).thenReturn("test_genre");

        Genre genre = genreMapper.mapRow(resultSet, 0);
        assertEquals(genre.getId(), 1);
        assertEquals(genre.getName(), "test_genre");
    }
}
