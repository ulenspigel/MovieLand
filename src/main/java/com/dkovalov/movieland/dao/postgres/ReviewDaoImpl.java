package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("reviewDao")
public class ReviewDaoImpl implements ReviewDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_REVIEW_SQL = "insert into ml.t_review(movie_id, user_id, text) values (?, ?, ?)";

    @Override
    public void addReview(Review review) {
        jdbcTemplate.update(INSERT_REVIEW_SQL, review.getMovie().getId(), review.getUser().getId(), review.getText());
    }
}
