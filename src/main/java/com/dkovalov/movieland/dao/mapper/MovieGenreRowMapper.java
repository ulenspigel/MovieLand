package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.MovieGenre;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieGenreRowMapper implements RowMapper<MovieGenre> {
    @Override
    public MovieGenre mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setMovieId(resultSet.getInt("movie_id"));
        GenreRowMapper genreRowMapper = new GenreRowMapper();
        movieGenre.setGenre(genreRowMapper.mapRow(resultSet, i));
        return movieGenre;
    }
}
