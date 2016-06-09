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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieRowMapper moviewRowMapper;
    @Autowired
    private GenreRowMapper genreRowMapper;
    @Value("${sql.movie.all}")
    private String fetchAllSQL;
    @Value("${sql.movie.genres}")
    private String fetchGenresSQL;

    @Override
    public List<Movie> getAll() {
        log.info("Start querying movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = jdbcTemplate.query(fetchAllSQL, moviewRowMapper);
        log.info("Finish querying movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Genre> getMovieGenres(int movieId) {
        log.info("Start fetching genres for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<Genre> genres = jdbcTemplate.query(fetchGenresSQL, new Object[]{movieId}, genreRowMapper);
        log.info("Finish fetching genres for movie with ID = {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return genres;
    }
}
