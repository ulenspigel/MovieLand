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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static RowMapper<Genre> rowMapper = new GenreRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.genre.forMovie}")
    private String fetchForMovieSQL;

    @Value("${sql.genre.allMovies}")
    private String fetchForAllMoviesSQL;

    @Value("${sql.genre.byName}")
    private String fetchByNameSQL;

    @Value("${sql.genre.add}")
    private String addGenreSQL;

    @Value("${sql.movie.addGenre}")
    private String addMovieGenreSQL;

    @Value("${sql.movie.deleteGenres}")
    private String deleteMovieGenresSQL;

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

    @Override
    public int getIdByName(String name) {
        log.info("Start searching genre by its name {}", name);
        long startTime = System.currentTimeMillis();
        Genre genre = jdbcTemplate.queryForObject(fetchByNameSQL, new Object[] {name}, rowMapper);
        log.debug("Fetched genre is {}", genre);
        log.info("Finish searching genre. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return genre.getId();
    }

    @Override
    public int add(String name) {
        log.info("Start adding genre with name {}", name);
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(addGenreSQL, new String[] {"genre_id"});
                statement.setString(1, name);
                return statement;
            }
        }, keyHolder);
        log.info("Finish adding genre. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addForMovie(int movieId, int genreId) {
        log.info("Start adding genre with ID={} for movie with ID={}", genreId, movieId);
        long startTime = System.currentTimeMillis();
        jdbcTemplate.update(addMovieGenreSQL, new Object[] {movieId, genreId});
        log.info("Row has been inserted. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteForMovie(int movieId) {
        log.info("Start deleting genres for a movie with ID={}", movieId);
        long startTime = System.currentTimeMillis();
        int rowsDeleted = jdbcTemplate.update(deleteMovieGenresSQL, new Object[] {movieId});
        log.info("{} rows have been deleted. Elapsed time - {} ms", rowsDeleted, System.currentTimeMillis() - startTime);
    }
}
