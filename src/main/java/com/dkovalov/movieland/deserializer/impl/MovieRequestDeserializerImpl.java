package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.deserializer.MovieRequestDeserializer;
import com.dkovalov.movieland.dto.MovieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieRequestDeserializerImpl extends AbstractRequestDeserializer implements MovieRequestDeserializer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public MovieRequest searchRequest(String json) {
        log.info("Start parsing movie search request {}", json);
        long startTime = System.currentTimeMillis();
        MovieRequest request = parseJson(json, MovieRequest.class);
        log.debug("Deserialized object is {}", request);
        log.info("Request has been parsed. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return request;
    }
}