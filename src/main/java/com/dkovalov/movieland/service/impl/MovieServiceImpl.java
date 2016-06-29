package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.cache.GenreCache;
import com.dkovalov.movieland.controller.error.AdminPrivilegesRequired;
import com.dkovalov.movieland.controller.error.ResourceNotFound;
import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.dto.MovieSearchRequest;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private GenreCache genreCache;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SecurityService securityService;

    @Override
    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = movieDao.getAll(ratingOrder, priceOrder);
        if (CollectionUtils.isEmpty(movies)) {
            log.error("Movies list is empty");
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
            log.error("Movie with ID {} was not found", id);
            throw new ResourceNotFound(e);
        }
        return movie;
    }

    @Override
    public List<Movie> search(MovieSearchRequest request) {
        List<Movie> movies = movieDao.search(request);
        if (CollectionUtils.isEmpty(movies)) {
            log.error("Movie with given search criteria was not found");
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

    @Override
    @Transactional
    public Movie add(int token, Movie movie) {
        validateAdminPrivileges(token);
        int movieId = movieDao.add(movie);
        movie.setId(movieId);
        genreService.addForMovie(movieId, movie.getGenres());
        countryService.addForMovie(movieId, movie.getCountries());
        return movie;
    }

    @Override
    @Transactional
    public void update(int token, Movie movie) {
        validateAdminPrivileges(token);
        movieDao.update(movie);
        // TODO: Null-pointer
        if (movie.getGenres().size() > 0) {
            genreService.deleteForMovie(movie.getId());
            genreService.addForMovie(movie.getId(), movie.getGenres());
        }
        if (movie.getCountries().size() > 0) {
            countryService.deleteForMovie(movie.getId());
            countryService.addForMovie(movie.getId(), movie.getCountries());
        }
    }

    private void validateAdminPrivileges(int token) {
        if (!securityService.checkTokenAdminRights(token)) {
            log.error("This operation requires administrator privileges");
            throw new AdminPrivilegesRequired();
        }
    }
}
