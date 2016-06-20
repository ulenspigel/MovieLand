package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.dao.mapper.MovieRowMapper;
import com.dkovalov.movieland.dao.util.QueryBuilder;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.dto.MovieSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QueryBuilder queryBuilder;

    @Value("${sql.movie.all}")
    private String fetchAllSQL;

    @Value("${sql.movie.byId}")
    private String fetchByIdSQL;

    @Override
    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        log.info("Start querying movies");
        long startTime = System.currentTimeMillis();
        String query = fetchAllSQL + queryBuilder.getMoviesOrderClause(ratingOrder, priceOrder);
        log.debug("Using query {}", query);
        List<Movie> movies = jdbcTemplate.query(query, movieRowMapper);
        log.info("Finish querying movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public Movie getById(int id) {
        log.info("Start querying movie with ID = {}", id);
        long startTime = System.currentTimeMillis();
        log.debug("Using query {}", fetchByIdSQL);
        Movie movie = jdbcTemplate.queryForObject(fetchByIdSQL, new Object[]{id}, movieRowMapper);
        log.info("Finish querying movie with ID = {}. Elapsed time - {} ms", id, System.currentTimeMillis() - startTime);
        return movie;
    }

    @Override
    public List<Movie> search(MovieSearchRequest request) {
        log.info("Start querying movies satisfying search request {}" + request);
        long startTime = System.currentTimeMillis();
        String query = fetchAllSQL + queryBuilder.getMoviesFilterPredicate(request);
        Object[] params = queryBuilder.getMoviesFilterParams(request);
        log.debug("Using query {} with params {}", query, params);
        List<Movie> movies = jdbcTemplate.query(query, params, movieRowMapper);
        log.info("Finis querying movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }
}
