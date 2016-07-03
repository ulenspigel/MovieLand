package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.User;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        when(resultSet.getString(any(String.class))).thenReturn("username").thenReturn("email");
        when(resultSet.getBoolean(any(String.class))).thenReturn(false);
        UserRowMapper mapper = new UserRowMapper();
        User user = mapper.mapRow(resultSet, 0);
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getDisplayName(), "username");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.isAdmin(), false);
    }
}
