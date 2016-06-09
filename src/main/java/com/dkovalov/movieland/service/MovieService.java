package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public void populateGenres(Movie movie) {
        movie.setGenres(movieDao.getMovieGenres(movie.getId()));
    }

    public void populateGenres(List<Movie> movies) {
        movies.forEach((movie) -> populateGenres(movie));
    }
}
