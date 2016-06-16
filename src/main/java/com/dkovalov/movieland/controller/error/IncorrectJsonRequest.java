package com.dkovalov.movieland.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect format or fields set of JSON request")
public class IncorrectJsonRequest extends RuntimeException {
}
