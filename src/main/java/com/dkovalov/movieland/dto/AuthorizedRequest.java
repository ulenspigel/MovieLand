package com.dkovalov.movieland.dto;

public class AuthorizedRequest<T> {
    private int token;
    private T requestEntity;

    public AuthorizedRequest(int token, T requestEntity) {
        this.token = token;
        this.requestEntity = requestEntity;
    }

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
