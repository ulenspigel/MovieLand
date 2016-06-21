package com.dkovalov.movieland.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This operation requires administrator privileges")
public class AdminPrivilegesRequired extends RuntimeException {
}
