package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.controller.error.IncorrectJsonRequest;
import com.dkovalov.movieland.deserializer.RequestDeserializer;
import com.dkovalov.movieland.dto.MovieSearchRequest;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.entity.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class RequestDeserializerImpl implements RequestDeserializer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    private <T> T parseJson(String json, Class<T> clazz) throws IncorrectJsonRequest {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException ioe) {
            throw new IncorrectJsonRequest(ioe);
        }
    }

    @Override
    public MovieSearchRequest searchRequest(String json) {
        log.info("Start parsing movie search request {}", json);
        long startTime = System.currentTimeMillis();
        MovieSearchRequest request = parseJson(json, MovieSearchRequest.class);
        log.debug("Deserialized object is {}", request);
        log.info("Request has been parsed. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return request;
    }

    @Override
    public UserCredentials authorizationRequest(String json) {
        UserCredentials credentials = parseJson(json, UserCredentials.class);
        return credentials;
    }

    @Override
    public Review addReviewRequest(String json) {
        log.info("Start parsing request for adding review {}", json);
        long startTime = System.currentTimeMillis();
        Review request = parseJson(json, Review.class);
        log.debug("Deserialized object is {}", request);
        log.info("Request has been parsed. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return request;
    }

    //TODO: Tests
    @Override
    public Movie editMovieRequest(String json) {
        log.info("Start parsing request for add/edit movie {}", json);
        long startTime = System.currentTimeMillis();
        Movie movie = parseJson(json, Movie.class);
        log.debug("Deserialized object is {}", movie);
        log.info("Request has been parsed. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return movie;
    }
}