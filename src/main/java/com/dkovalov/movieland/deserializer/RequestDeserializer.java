package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.dto.AuthorizedRequest;
import com.dkovalov.movieland.dto.MovieRequest;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.Review;

import java.io.IOException;

public interface RequestDeserializer {
    MovieRequest searchRequest(String json);
    UserCredentials authorizationRequest(String json);
    AuthorizedRequest<Review> reviewManipulationRequest(String json);
}
