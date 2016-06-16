package com.dkovalov.movieland.entity;

import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Review {
    @JsonProperty("reviewId")
    @JsonView(JsonDisplayScheme.ReviewConcise.class)
    private int id;

    @JsonProperty("movieId")
    private int movieId;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("review")
    @JsonView(JsonDisplayScheme.MovieFull.class)
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                '}';
    }
}
