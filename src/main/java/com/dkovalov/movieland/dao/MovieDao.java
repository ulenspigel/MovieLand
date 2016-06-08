package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.entity.Movie;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
    List<Genre> getMovieGenres(int movieId);
}
