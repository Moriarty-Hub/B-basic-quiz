package com.thoughtworks.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "The user id is not exist";
    }

    public Integer getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
