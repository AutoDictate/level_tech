package com.level.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredJwtTokenException extends RuntimeException {

    public ExpiredJwtTokenException(String message) {
        super(message);
    }

    public ExpiredJwtTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
