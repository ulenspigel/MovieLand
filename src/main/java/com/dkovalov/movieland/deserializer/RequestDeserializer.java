package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.dto.MovieSearchRequest;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.entity.Review;

public interface RequestDeserializer {
    MovieSearchRequest searchRequest(String json);
    UserCredentials authorizationRequest(String json);
    Review addReviewRequest(String json);
    Movie editMovieRequest(String json);
}
