package com.thoughtworks.exception;

public class UserIdNotMatchException extends RuntimeException {

    @Override
    public String getMessage() {
        return "The user id in the education request body is not match with the user id in the url";
    }

    public Integer getErrorCode() {
        return 400;
    }
}
