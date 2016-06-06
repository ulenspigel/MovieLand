package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MovieDaoImpl implements MovieDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String INSERT_MOVIE_SQL =
            "insert into ml.t_movie(movie_title, year, country, description, rating, price) values (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_MOVIE_GENRE = "insert into ml.t_movie_genre(movie_id, genre_id) values (?, ?)";

    @Override
    public void addMovie(Movie movie) {
        jdbcTemplate.update(INSERT_MOVIE_SQL, movie.getTitle(), movie.getYear(), movie.getCountry(),
                movie.getDescription(), movie.getRating(), movie.getPrice());
        for (int genreId: movie.getGenres()) {
            jdbcTemplate.update();
        }
    }
}
