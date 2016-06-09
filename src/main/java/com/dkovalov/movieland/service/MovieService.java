package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.CountryDao;
import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private ReviewDao reviewDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    public void populateGenres(Movie movie) {
        movie.setGenres(genreDao.getForMovie(movie.getId()));
    }

    public void populateGenres(List<Movie> movies) {
        movies.forEach((movie) -> populateGenres(movie));
    }

    public void populateCountries(Movie movie) {
        movie.setCountries(countryDao.getForMovie(movie.getId()));
    }

    public void populateReviews(Movie movie) {
        movie.setReviews(reviewDao.getForMovie(movie.getId()));
    }
}
