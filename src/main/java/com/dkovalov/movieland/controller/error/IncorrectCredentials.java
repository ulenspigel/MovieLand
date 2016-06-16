package com.dkovalov.movieland.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect combination of username and password")
public class IncorrectCredentials extends RuntimeException {
    public IncorrectCredentials() {
        super();
    }

    public IncorrectCredentials(Throwable error) {
        super(error);
    }
}
