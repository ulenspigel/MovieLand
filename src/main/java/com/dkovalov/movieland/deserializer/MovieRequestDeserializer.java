package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.entity.MovieRequest;

public interface MovieRequestDeserializer {
    MovieRequest searchRequest(String json);
}
