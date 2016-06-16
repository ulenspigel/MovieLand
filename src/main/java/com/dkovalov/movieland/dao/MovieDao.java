package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.dto.MovieRequest;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll(String ratingOrder, String priceOrder);
    List<Movie> search(MovieRequest request);
    Movie getById(int id);
}
