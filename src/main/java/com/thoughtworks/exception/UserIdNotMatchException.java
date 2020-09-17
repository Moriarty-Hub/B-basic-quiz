package com.thoughtworks.exception;

import org.springframework.http.HttpStatus;

// GTB: - 用 UserNotFoundException 就行了
public class UserIdNotMatchException extends RuntimeException {

    @Override
    public String getMessage() {
        return "The user id in the education request body is not match with the user id in the url";
    }

    public Integer getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
