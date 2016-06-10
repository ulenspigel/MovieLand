package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Country;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        when(resultSet.getString(any(String.class))).thenReturn("test country");

        CountryRowMapper countryMapper = new CountryRowMapper();
        Country country = countryMapper.mapRow(resultSet, 0);
        assertEquals(country.getId(), 1);
        assertEquals(country.getName(), "test country");
    }
}
