package com.dkovalov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserToken {
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private LocalDateTime generationTime; // TODO: store expiration time
    @JsonProperty("token")
    private int token;
    @JsonIgnore
    private boolean isAdmin;

    public UserToken(int userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
