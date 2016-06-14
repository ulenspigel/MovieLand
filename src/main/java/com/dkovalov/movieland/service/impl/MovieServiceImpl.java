package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.cache.GenreCache;
import com.dkovalov.movieland.controller.error.ResourceNotFound;
import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.entity.MovieRequest;
import com.dkovalov.movieland.service.CountryService;
import com.dkovalov.movieland.service.MovieService;
import com.dkovalov.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreCache genreCache;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = movieDao.getAll(ratingOrder, priceOrder);
        if (movies == null || movies.size() == 0) {
            throw new ResourceNotFound();
        }
        return movies;
    }

    @Override
    public Movie getById(int id) {
        Movie movie = null;
        try {
            movie = movieDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFound();
        }
        return movie;
    }

    @Override
    public List<Movie> search(MovieRequest request) {
        List<Movie> movies = movieDao.search(request);
        if (movies == null || movies.size() == 0) {
            throw new ResourceNotFound();
        }
        return movies;
    }

    @Override
    public void populateGenres(Movie movie) {
        movie.setGenres(genreCache.getForMovie(movie.getId()));
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
