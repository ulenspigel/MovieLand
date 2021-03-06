package com.dkovalov.movieland.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Token doesn't correspond to user's identifier mentioned in request")
public class TokenUserIdMismatch extends RuntimeException {
    public TokenUserIdMismatch() {
        super();
    }

    public TokenUserIdMismatch(Throwable error) {
        super(error);
    }

    public TokenUserIdMismatch(String cause) {
        super(cause);
    }
}
