package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Review;
import java.util.List;

public interface ReviewDao {
    List<Review> getForMovie(int movieId);
    int add(Review review);
    Review getById(int reviewId);
    int delete(int reviewId);
}
