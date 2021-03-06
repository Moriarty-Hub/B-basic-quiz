package com.thoughtworks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    // GTB: - 有HttpStatus.BAD_REQUEST可以用，了解一下
    private static final String DESCRIPTION_OF_ERROR_CODE_400 = "Bad Request";
    private static final String DESCRIPTION_OF_ERROR_CODE_404 = "Not Found";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Error error = Error.builder()
                .timestamp(new Date().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(DESCRIPTION_OF_ERROR_CODE_400)
                .message(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> userNotFoundExceptionHandler(UserNotFoundException exception) {
        Error error = Error.builder()
                .timestamp(new Date().toString())
                .status(exception.getErrorCode())
                .error(DESCRIPTION_OF_ERROR_CODE_404)
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIdNotMatchException.class)
    public ResponseEntity<Error> userIdNotMatchExceptionHandler(UserIdNotMatchException exception) {
        Error error = Error.builder()
                .timestamp(new Date().toString())
                .status(exception.getErrorCode())
                .error(DESCRIPTION_OF_ERROR_CODE_400)
                .message(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

}
