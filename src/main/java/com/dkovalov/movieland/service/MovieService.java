package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.dto.MovieRequest;
import com.dkovalov.movieland.entity.Review;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(String ratingOrder, String priceOrder);
    Movie getById(int id);
    List<Movie> search(MovieRequest request);
    void populateGenres(Movie movie);
    void populateGenres(List<Movie> movies);
    void populateCountries(Movie movie);
    void populateReviews(Movie movie);
}
