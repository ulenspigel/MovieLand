package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.entity.Review;
import java.util.List;

public interface ReviewService {
    List<Review> getForMovie(int movieId);
    Review add(AuthorizedRequest<Review> request);
}
