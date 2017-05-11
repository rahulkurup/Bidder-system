package com.yieldlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class AppWideExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = "Something went wrong during IO Operation")  // 409
    @ExceptionHandler(IOException.class)
    public void ioException() {
    }
}