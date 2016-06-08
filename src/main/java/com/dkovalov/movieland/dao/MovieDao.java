package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Movie;
import java.util.List;

public interface MovieDao {
    void addMovie(Movie movie);
    Movie getByName(String name);
    //========================
    List<Movie> getAll();
}
