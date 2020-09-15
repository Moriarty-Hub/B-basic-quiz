package com.thoughtworks.exception;

public class UserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "The user id is not exist";
    }

    public Integer getErrorCode() {
        return 404;
    }
}
