package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.dao.mapper.ReviewRowMapper;
import com.dkovalov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.review.forMovie.limited}")
    private String fetchForMovieSQL;

    @Value("${sql.review.insert}")
    private String insertSQL;

    @Value("${sql.review.byId}")
    private String fetchByIdSQL;

    @Value("${movie.review.limit}:2")
    private int fetchLimit;

    @Override
    public List<Review> getForMovie(int movieId) {
        log.info("Start querying reviews for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<Review> reviews = jdbcTemplate.query(fetchForMovieSQL, new Object[]{movieId, fetchLimit}, new ReviewRowMapper());
        log.info("Finish querying reviews for movie with ID = {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return reviews;
    }

    @Override
    public int add(Review review) {
        log.info("Start inserting review {}", review);
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // TODO: replace with lambda
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(insertSQL, new String[] {"review_id"});
                statement.setInt(1, review.getUserId());
                statement.setInt(2, review.getMovieId());
                statement.setString(3, review.getText());
                return statement;
            }
        }, keyHolder);
        log.info("Review inserted. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Review getById(int reviewId) {
        log.info("Start querying reviews with ID = {}", reviewId);
        long startTime = System.currentTimeMillis();
        Review review = jdbcTemplate.queryForObject(fetchByIdSQL, new Object[] {reviewId}, new ReviewRowMapper());
        log.info("Review {} queried. Elapsed time - {} ms", review.getId(), System.currentTimeMillis() - startTime);
        return review;
    }

    @Override
    public int delete(int reviewId) {
        return 0;
    }
}
