package com.dkovalov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserToken {
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private LocalDateTime generationTime;
    @JsonProperty("token")
    private int token;

    public UserToken(int userId) {
        this.userId = userId;
        generationTime = LocalDateTime.now();
        token = (userId + generationTime.toString()).hashCode();
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getGenerationTime() {
        return generationTime;
    }

    public int getToken() {
        return token;
    }
}
