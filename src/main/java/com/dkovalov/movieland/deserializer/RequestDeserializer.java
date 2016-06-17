package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.dto.MovieRequest;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Review;

public interface RequestDeserializer {
    MovieRequest searchRequest(String json);
    UserCredentials authorizationRequest(String json);
    Review addReviewRequest(String json);
}
