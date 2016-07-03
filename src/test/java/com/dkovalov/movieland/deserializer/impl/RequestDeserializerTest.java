package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.controller.error.IncorrectJsonRequest;
import com.dkovalov.movieland.deserializer.RequestDeserializer;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.entity.Review;
import org.junit.Test;

import java.math.BigDecimal;

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
        Review request = deserializer.addReviewRequest(requestJSON);
        assertEquals(request.getMovieId(), 1);
        assertEquals(request.getUserId(), 10);
        assertEquals(request.getText(), "Test review");
        request = deserializer.addReviewRequest(requestJSON.replace("movieId", "movie_id"));
    }

    @Test(expected = IncorrectJsonRequest.class)
    public void testEditMovieRequest() {
        String requestJSON = "{\"id\":1,\"title\":\"Title\",\"originalTitle\":\"Original title\",\"description\":" +
                "\"Description\",\"yearOfRelease\":2000,\"countries\":[{\"country\":\"country\"}],\"rating\":10.0," +
                "\"genres\":[{\"genre\":\"genre1\"},{\"genre\":\"genre2\"}],\"price\":123.45}";
        Movie movie = deserializer.editMovieRequest(requestJSON);
        assertEquals(movie.getId(), 1);
        assertEquals(movie.getTitle(), "Title");
        assertEquals(movie.getOriginalTitle(), "Original title");
        assertEquals(movie.getDescription(), "Description");
        assertEquals(movie.getYear(), 2000);
        assertEquals(movie.getRating(), BigDecimal.valueOf(10.0));
        assertEquals(movie.getPrice(), BigDecimal.valueOf(123.45));
        assertEquals(movie.getCountries().size(), 1);
        assertEquals(movie.getCountries().get(0).getName(), "country");
        assertEquals(movie.getGenres().size(), 2);
        assertEquals(movie.getGenres().get(1).getName(), "genre2");
        movie = deserializer.editMovieRequest(requestJSON.replace("originalTitle", "original_title"));
    }
}