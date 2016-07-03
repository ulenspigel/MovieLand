package com.dkovalov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserToken {
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private LocalDateTime expirationTime;
    @JsonProperty("token")
    private int token;
    @JsonIgnore
    private boolean isAdmin;

    public UserToken(int userId, boolean isAdmin, int lifetimeSeconds) {
        this.userId = userId;
        this.isAdmin = isAdmin;
        expirationTime = LocalDateTime.now().plusSeconds(lifetimeSeconds);
        token = (userId + expirationTime.toString()).hashCode();
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public int getToken() {
        return token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
