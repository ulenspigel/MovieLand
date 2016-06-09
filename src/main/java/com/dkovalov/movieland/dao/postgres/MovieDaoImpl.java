package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.dao.mapper.GenreRowMapper;
import com.dkovalov.movieland.dao.mapper.MovieRowMapper;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("movieDao")
public class MovieDaoImpl implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private static final String ORDER_BY_CLAUSE = " order by ";
    private static final String RATING_COLUMN_NAME = "rating";
    private static final String PRICE_COLUMN_NAME = "price";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sql.movie.all}")
    private String fetchAllSQL;
    @Value("${sql.movie.byId}")
    private String fetchByIdSQL;

    @Override
    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        log.info("Start querying movies");
        long startTime = System.currentTimeMillis();
        String orderClause = "";
        if (ratingOrder != null || priceOrder != null) {
            orderClause = ORDER_BY_CLAUSE;
            if (ratingOrder != null) {
                orderClause += RATING_COLUMN_NAME + " " + ratingOrder;
                if (priceOrder != null) {
                    orderClause += "," + PRICE_COLUMN_NAME + " " + priceOrder;
                }
            } else {
                if (priceOrder != null) {
                    orderClause += PRICE_COLUMN_NAME + " " + priceOrder;
                }
            }
        }
        List<Movie> movies = jdbcTemplate.query(fetchAllSQL + orderClause, movieRowMapper);
        log.info("Finish querying movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public Movie getById(int id) {
        log.info("Start querying movie with ID = {}", id);
        long startTime = System.currentTimeMillis();
        Movie movie = jdbcTemplate.queryForObject(fetchByIdSQL, new Object[]{id}, movieRowMapper);
        log.info("Finish querying movie with ID = {}. Elapsed time - {} ms", id, System.currentTimeMillis() - startTime);
        return movie;
    }
}
