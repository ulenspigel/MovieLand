package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("movie_id"));
        movie.setTitle(resultSet.getString("movie_title"));
        movie.setOriginalTitle(resultSet.getString("original_title"));
        movie.setYear(resultSet.getInt("year"));
        movie.setCountry(resultSet.getString("country"));
        movie.setDescription(resultSet.getString("description"));
        movie.setRating(resultSet.getBigDecimal("rating"));
        movie.setPrice(resultSet.getBigDecimal("price"));
        return movie;
    }
}
