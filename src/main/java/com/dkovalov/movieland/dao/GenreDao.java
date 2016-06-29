package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;

import java.util.List;

public interface GenreDao {
    List<Genre> getForMovie(int movieId);
    List<MovieGenre> getForAllMovies();
    int getIdByName(String name);
    int add(String name);
    void addForMovie(int movieId, int genreId);
    void deleteForMovie(int movieId);
}
