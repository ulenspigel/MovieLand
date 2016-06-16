package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;

import java.util.List;

public interface GenreDao {
    List<Genre> getForMovie(int movieId);
    List<MovieGenre> getForAllMovies();
}
