package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.CountryService;
import com.dkovalov.movieland.service.GenreService;
import com.dkovalov.movieland.service.MovieService;
import com.dkovalov.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        return movieDao.getAll(ratingOrder, priceOrder);
    }

    @Override
    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    @Override
    public void populateGenres(Movie movie) {
        movie.setGenres(genreService.getForMovie(movie.getId()));
    }

    @Override
    public void populateGenres(List<Movie> movies) {
        movies.forEach((movie) -> populateGenres(movie));
    }

    @Override
    public void populateCountries(Movie movie) {
        movie.setCountries(countryService.getForMovie(movie.getId()));
    }

    @Override
    public void populateReviews(Movie movie) {
        movie.setReviews(reviewService.getForMovie(movie.getId()));
    }
}
