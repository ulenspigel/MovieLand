package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.TokenUserIdMismatch;
import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.entity.Review;
import com.dkovalov.movieland.service.ReviewService;
import com.dkovalov.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private SecurityService securityService;

    public List<Review> getForMovie(int movieId) {
        return reviewDao.getForMovie(movieId);
    }

    @Override
    public Review add(AuthorizedRequest<Review> request) {
        if (!securityService.isUsersToken(request.getToken(), request.getRequestEntity().getUserId())) {
            throw new TokenUserIdMismatch();
        }
        Review review = request.getRequestEntity();
        int reviewId = reviewDao.add(review);
        review.setId(reviewId);
        return review;
    }

    @Override
    public Review delete(AuthorizedRequest<Review> request) {
        return null;
    }
}
