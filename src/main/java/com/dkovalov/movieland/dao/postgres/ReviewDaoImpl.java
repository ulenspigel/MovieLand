package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.dao.mapper.ReviewRowMapper;
import com.dkovalov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reviewDao")
public class ReviewDaoImpl implements ReviewDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sql.review.forMovie.limited}")
    private String fetchForMovieSQL;
    @Value("${movie.review.limit}")
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
}
