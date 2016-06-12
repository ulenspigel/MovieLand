package com.dkovalov.movieland.deserializer;

import com.dkovalov.movieland.entity.MovieRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MovieRequestDeserializer extends JsonDeserializer<MovieRequest> {
    private static final String TITLE_PROPERTY = "title";
    private static final String YEAR_PROPERTY = "year_of_release";
    private static final String COUNTRY_PROPERTY = "country";
    private static final String GENRE_PROPERTY = "genre";

    @Override
    public MovieRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        MovieRequest MovieRequest = new MovieRequest();
        if (node.hasNonNull(TITLE_PROPERTY)) {
            MovieRequest.setTitle(node.get(TITLE_PROPERTY).asText());
        }
        if (node.hasNonNull(YEAR_PROPERTY) && node.get(YEAR_PROPERTY).isInt()) {
            MovieRequest.setYear(node.get(YEAR_PROPERTY).asInt());
        }
        if (node.hasNonNull(COUNTRY_PROPERTY)) {
            MovieRequest.setCountry(node.get(COUNTRY_PROPERTY).asText());
        }
        if (node.hasNonNull(GENRE_PROPERTY)) {
            MovieRequest.setGenre(node.get(GENRE_PROPERTY).asText());
        }
        return MovieRequest;
    }
}
