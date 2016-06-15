package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.dto.UserCredentials;

public interface SecurityRequestDeserializer {
    UserCredentials authorizationRequest(String json);
}
