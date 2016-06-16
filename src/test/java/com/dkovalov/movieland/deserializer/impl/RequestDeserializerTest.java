package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.controller.error.IncorrectJsonRequest;
import com.dkovalov.movieland.deserializer.RequestDeserializer;
import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Review;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestDeserializerTest {
    private RequestDeserializer deserializer = new RequestDeserializerImpl();

    @Test(expected = IncorrectJsonRequest.class)
    public void testAuthorizationRequest() {
        String requestJSON = "{\"login\": \"test_login\", \"password\": \"test_password\"}";
        UserCredentials credentials = deserializer.authorizationRequest(requestJSON);
        assertEquals(credentials.getLogin(), "test_login");
        assertEquals(credentials.getPassword(), "test_password");
        credentials = deserializer.authorizationRequest(requestJSON.replace("login", "email"));
    }

    @Test(expected = IncorrectJsonRequest.class)
    public void testAddReviewRequest() {
        String requestJSON = "{\"token\": 123, \"request\": {\"movieId\": 1, \"userId\": 10, \"review\": \"Test review\"}}";
        AuthorizedRequest<Review> request = deserializer.reviewManipulationRequest(requestJSON);
        assertEquals(request.getToken(), 123);
        assertEquals(request.getRequestEntity().getMovieId(), 1);
        assertEquals(request.getRequestEntity().getUserId(), 10);
        assertEquals(request.getRequestEntity().getText(), "Test review");
        request = deserializer.reviewManipulationRequest(requestJSON.replace("movieId", "movie_id"));
    }
}