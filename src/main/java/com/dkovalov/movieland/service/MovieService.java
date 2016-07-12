package com.dkovalov.movieland.service;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.dto.MovieSearchRequest;
import java.util.List;

public interface MovieService {
    List<Movie> getAll(String ratingOrder, String priceOrder);
    Movie getById(int id);
    List<Movie> search(MovieSearchRequest request);
    void populateGenres(Movie movie);
    void populateGenres(List<Movie> movies);
    void populateCountries(Movie movie);
    void populateReviews(Movie movie);
    Movie add(int token, Movie movie);
    void update(int token, Movie movie);
    void markForDeletion(int token, int id);
    int unmarkForDeletion(int token, int id);
    void deleteMarkedMovies();
}
