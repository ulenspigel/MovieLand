package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.dao.mapper.GenreRowMapper;
import com.dkovalov.movieland.dao.mapper.MovieGenreRowMapper;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.genre.forMovie}")
    private String fetchForMovieSQL;

    @Value("${sql.genre.allMovies}")
    private String fetchForAllMoviesSQL;

    @Override
    public List<Genre> getForMovie(int movieId) {
        log.info("Start querying genres for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<Genre> genres = jdbcTemplate.query(fetchForMovieSQL, new Object[]{movieId}, new GenreRowMapper());
        log.info("Finish querying genres for movie with ID = {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return genres;
    }

    @Override
    public List<MovieGenre> getForAllMovies() {
        log.info("Start querying genres for all movies");
        long startTime = System.currentTimeMillis();
        List<MovieGenre> movieGenres = jdbcTemplate.query(fetchForAllMoviesSQL, new MovieGenreRowMapper());
        log.info("Finish querying genres for movies. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movieGenres;
    }
}
