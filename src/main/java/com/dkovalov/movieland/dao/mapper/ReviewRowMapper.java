package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.Review;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt("review_id"));
        review.setMovieId(resultSet.getInt("movie_id"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setText(resultSet.getString("text"));
        return review;
    }
}
