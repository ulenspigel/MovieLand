package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.entity.Review;
import com.dkovalov.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    public List<Review> getForMovie(int movieId) {
        return reviewDao.getForMovie(movieId);
    }
}
