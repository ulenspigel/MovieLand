package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.deserializer.impl.RequestDeserializerImpl;
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
    public Review addReview(@RequestBody String request) {
        log.info("Received request for review adding");
        long startTime = System.currentTimeMillis();
        Review review = reviewService.add(deserializer.reviewManipulationRequest(request));
        log.debug("Added entry is {}", review);
        log.info("Review with ID {} was successfully added. Elapsed time - {} ms", review.getId(),
                System.currentTimeMillis() - startTime);
        return review;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@RequestBody String request) {
        log.info("Received request for review deletion {}", request);
        long startTime = System.currentTimeMillis();
        Review deletedReview = reviewService.delete(deserializer.reviewManipulationRequest(request));
        log.info("Review with ID {} was deleted. Elapsed time - {} ms", deletedReview.getId(),
                System.currentTimeMillis() - startTime);
    }
}
