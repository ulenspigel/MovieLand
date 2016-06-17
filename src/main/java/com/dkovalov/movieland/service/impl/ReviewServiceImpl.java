package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.ResourceNotFound;
import com.dkovalov.movieland.controller.error.TokenUserIdMismatch;
import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.entity.Review;
import com.dkovalov.movieland.service.ReviewService;
import com.dkovalov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String TOKEN_USER_MISMATCH_ERROR =
            "Token doesn't correspond to user's identifier mentioned in request";

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
            log.error(TOKEN_USER_MISMATCH_ERROR);
            throw new TokenUserIdMismatch(TOKEN_USER_MISMATCH_ERROR);
        }
        Review review = request.getRequestEntity();
        int reviewId = reviewDao.add(review);
        review.setId(reviewId);
        return review;
    }

    @Override
    public int delete(AuthorizedRequest<Review> request) {
        Review review = null;
        try {
            review = reviewDao.getById(request.getRequestEntity().getId());
        } catch (EmptyResultDataAccessException e) {
            log.error("Review with a specified ID was not found in database");
            throw new ResourceNotFound(e);
        }
        if (!securityService.isUsersToken(request.getToken(), review.getUserId())) {
            log.error(TOKEN_USER_MISMATCH_ERROR);
            throw new TokenUserIdMismatch(TOKEN_USER_MISMATCH_ERROR);
        }
        return reviewDao.delete(request.getRequestEntity().getId());
    }
}
