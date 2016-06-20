package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.dto.MovieSearchRequest;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll(String ratingOrder, String priceOrder);
    List<Movie> search(MovieSearchRequest request);
    Movie getById(int id);
}
