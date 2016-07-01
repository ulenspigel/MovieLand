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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Value("${sql.movie.insert}")
    private String movieInsertSQL;

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
        log.info("Finish querying movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public int add(Movie movie) {
        log.info("Start inserting movie {}", movie);
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(movieInsertSQL, new String[] {"movie_id"});
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getOriginalTitle());
                statement.setInt(3, movie.getYear());
                statement.setString(4, movie.getDescription());
                statement.setBigDecimal(5, movie.getRating());
                statement.setBigDecimal(6, movie.getPrice());
                return statement;
            }
        }, keyHolder);
        int movieId = keyHolder.getKey().intValue();
        log.info("Finish inserting movie. Generated ID is {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return movieId;
    }

    @Override
    public int update(Movie movie) {
        log.info("Start updating movie with new values {}", movie);
        long startTime = System.currentTimeMillis();
        String updateQuery = queryBuilder.getMovieUpdateStatement(movie);
        int rowsUpdated = 0;
        if (updateQuery != null) {
            rowsUpdated = jdbcTemplate.update(updateQuery, new Object[]{movie.getId()});
            log.info("Finish updating movie. Affected {} row(s). Elapsed time - {} ms", rowsUpdated,
                    System.currentTimeMillis() - startTime);
        } else {
            log.info("Nothing to update in T_MOVIE table: no columns have been changed");
        }
        return rowsUpdated;
    }
}
