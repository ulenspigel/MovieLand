package com.dkovalov.movieland.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizedRequest<T> {
    @JsonProperty("token")
    private int token;
    @JsonProperty("request")
    private T requestEntity;

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public T getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(T requestEntity) {
        this.requestEntity = requestEntity;
    }
}
