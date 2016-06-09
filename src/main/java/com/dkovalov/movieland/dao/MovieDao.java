package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Movie;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll(String ratingOrder, String priceOrder);
    Movie getById(int id);
}
