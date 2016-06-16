package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.deserializer.SecurityRequestDeserializer;
import com.dkovalov.movieland.dto.UserCredentials;
import org.springframework.stereotype.Service;

@Service
public class SecurityRequestDeserializerImpl extends AbstractRequestDeserializer implements SecurityRequestDeserializer {
    @Override
    public UserCredentials authorizationRequest(String json) {
        UserCredentials credentials = parseJson(json, UserCredentials.class);
        return credentials;
    }
}
