package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.dto.MovieRequest;

public interface MovieRequestDeserializer {
    MovieRequest searchRequest(String json);
}
