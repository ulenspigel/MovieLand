package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.dao.mapper.MovieRowMapper;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository("movieDao")
public class MovieDaoImpl implements MovieDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_MOVIE_SQL =
            "insert into ml.t_movie(movie_title, original_title, year, country, description, rating, price) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_MOVIE_GENRE = "insert into ml.t_movie_genre(movie_id, genre_id) values (?, ?)";
    private static final String LOOKUP_MOVIE_SQL = "select * from ml.t_movie where movie_title = ?";

    @Override
    public void addMovie(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(INSERT_MOVIE_SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getOriginalTitle());
                statement.setInt(3, movie.getYear());
                statement.setString(4, movie.getCountry());
                statement.setString(5, movie.getDescription());
                statement.setBigDecimal(6, movie.getRating());
                statement.setBigDecimal(7, movie.getPrice());
                return statement;
            }
        }, keyHolder);

        for (Genre genre: movie.getGenres()) {
            jdbcTemplate.update(INSERT_MOVIE_GENRE, keyHolder.getKeys().get("movie_id"), genre.getId());
        }
    }

    @Override
    public Movie getByName(String name) {
        return jdbcTemplate.queryForObject(LOOKUP_MOVIE_SQL, new Object[]{name}, new MovieRowMapper());
    }
}
