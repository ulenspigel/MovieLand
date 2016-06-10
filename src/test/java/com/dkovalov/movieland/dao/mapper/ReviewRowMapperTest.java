package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Review;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1).thenReturn(2).thenReturn(3);
        when(resultSet.getString(any(String.class))).thenReturn("test review");

        ReviewRowMapper reviewMapper = new ReviewRowMapper();
        Review review = reviewMapper.mapRow(resultSet, 0);
        assertEquals(review.getId(), 1);
        assertEquals(review.getMovieId(), 2);
        assertEquals(review.getUserId(), 3);
        assertEquals(review.getText(), "test review");
    }
}
