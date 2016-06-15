package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.controller.error.IncorrectJsonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class AbstractRequestDeserializer {
    private ObjectMapper mapper = new ObjectMapper();

    protected <T> T parseJson(String json, Class<T> clazz) throws IncorrectJsonRequest {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException ioe) {
            throw new IncorrectJsonRequest();
        }
    }
}
