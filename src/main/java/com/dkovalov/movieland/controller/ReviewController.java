package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.deserializer.impl.RequestDeserializerImpl;
import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.entity.Review;
import com.dkovalov.movieland.service.ReviewService;
import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/review")
public class ReviewController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RequestDeserializerImpl deserializer;

    @JsonView(JsonDisplayScheme.ReviewConcise.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Review addReview(@RequestHeader("User-Token") int token, @RequestBody String request) {
        log.info("Received request for review adding");
        long startTime = System.currentTimeMillis();
        AuthorizedRequest<Review> addRequest = new AuthorizedRequest<>(token, deserializer.addReviewRequest(request));
        Review review = reviewService.add(addRequest);
        log.debug("Added entry is {}", review);
        log.info("Review with ID {} was successfully added. Elapsed time - {} ms", review.getId(),
                System.currentTimeMillis() - startTime);
        return review;
    }

    @RequestMapping(value="/{reviewId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@RequestHeader("User-Token") int token, @PathVariable int reviewId) {
        log.info("Received request for deleting a review with ID {}", reviewId);
        long startTime = System.currentTimeMillis();
        Review review = new Review();
        review.setId(reviewId);
        AuthorizedRequest<Review> deleteRequest = new AuthorizedRequest<>(token, review);
        reviewService.delete(deleteRequest);
        log.info("Review with ID {} was deleted. Elapsed time - {} ms", reviewId, System.currentTimeMillis() - startTime);
    }
}
